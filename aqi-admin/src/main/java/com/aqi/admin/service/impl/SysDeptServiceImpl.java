package com.aqi.admin.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.aqi.admin.constant.AdminCommonConstant;
import com.aqi.admin.entity.base.SysDept;
import com.aqi.admin.entity.base.SysUser;
import com.aqi.admin.entity.dto.SysDeptDTO;
import com.aqi.admin.entity.vo.Option;
import com.aqi.admin.entity.vo.SysDeptVo;
import com.aqi.admin.entity.wrapper.SysDeptWrapper;
import com.aqi.admin.mapper.SysDeptMapper;
import com.aqi.admin.service.ISysDeptService;
import com.aqi.admin.service.ISysUserService;
import com.aqi.common.core.utils.BeanCopyUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements ISysDeptService {
    private final ISysUserService sysUserService;


    @Override
    public List<Option> departTreeSelect() {
        List<SysDept> list = this.list(new LambdaQueryWrapper<SysDept>()
                .select(SysDept::getDeptId, SysDept::getParentId, SysDept::getDeptName));

        Set<Long> parentIds = list.stream()
                .map(SysDept::getParentId)
                .collect(Collectors.toSet());

        Set<Long> deptIds = list.stream()
                .map(SysDept::getDeptId)
                .collect(Collectors.toSet());

        List<Long> rootIds = CollectionUtil.subtractToList(parentIds, deptIds);

        List<Option> resultList = new ArrayList<>();
        for (Long rootId : rootIds) {
            resultList.addAll(recurDeptOptions(rootId, list));
        }
        return resultList;
    }

    /**
     * 递归生成菜单下拉层级列表
     */
    private static List<Option> recurDeptOptions(Long parentId, List<SysDept> deptList) {
        List<Option> list = CollectionUtil.emptyIfNull(deptList).stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> new Option(String.valueOf(menu.getDeptId()), menu.getDeptName(), recurDeptOptions(menu.getDeptId(), deptList)))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        return list;
    }

    @Override
    public List<SysDeptVo> queryDeptList(SysDeptDTO sysDeptDTO) {
        List<SysDeptVo> resultList = new ArrayList<>();
        LambdaQueryWrapper<SysDept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(sysDeptDTO.getDeptName()), SysDept::getDeptName, sysDeptDTO.getDeptName());
        List<SysDept> list = list(queryWrapper);
        if (CollectionUtil.isNotEmpty(list)) {
            Set<Long> parentIds = list.stream()
                    .map(SysDept::getParentId)
                    .collect(Collectors.toSet());

            Set<Long> deptIds = list.stream()
                    .map(SysDept::getDeptId)
                    .collect(Collectors.toSet());
            List<Long> rootIds = CollectionUtil.subtractToList(parentIds, deptIds);
            for (Long rootId : rootIds) {
                // 递归
                resultList.addAll(recurDepts(rootId, list));
            }
        }
        return resultList;
    }

    private List<SysDeptVo> recurDepts(Long parentId, List<SysDept> deptList) {
        return CollectionUtil.emptyIfNull(deptList)
                .stream()
                .filter(dept -> dept.getParentId().equals(parentId))
                .map(entity -> {
                    SysDeptVo sysDeptVo = SysDeptWrapper.build().entityVO(entity);
                    List<SysDeptVo> children = recurDepts(entity.getDeptId(), deptList);
                    sysDeptVo.setChildren(children);
                    return sysDeptVo;
                }).collect(Collectors.toList());
    }

    @Override
    public List<SysDeptVo> getDepartList() {
        LambdaQueryWrapper<SysDept> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(SysDept::getParentId).orderByDesc(SysDept::getOrderNum);
        List<SysDept> list = list(lambdaQueryWrapper);
        return SysDeptWrapper.build().listVO(list);
    }

    @Override
    public void saveDept(SysDeptDTO sysDeptDTO) {
        SysDept sysDept = BeanCopyUtils.copy(sysDeptDTO, SysDept.class);
        // 部门路径
        String treePath = generateDeptTreePath(sysDeptDTO.getParentId());
        sysDept.setTreePath(treePath);
        if (sysDeptDTO.getDeptId() == null) {
            long deptId = IdWorker.getId();
            sysDept.setDeptId(deptId);
        }
        saveOrUpdate(sysDept);
    }

    private String generateDeptTreePath(Long parentId) {
        String treePath = null;
        if (AdminCommonConstant.TOP_DEPT.equals(parentId)) {
            treePath = String.valueOf(parentId);
        } else {
            SysDept parent = this.getById(parentId);
            if (parent != null) {
                treePath = parent.getTreePath() + "," + parent.getDeptId();
            }
        }
        return treePath;
    }

    @Override
    public void delDeptById(Long deptId) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getDeptId, deptId);
        long count = sysUserService.count(queryWrapper);
        if (count > 0) {
            throw new RuntimeException("有用户配置了该部门导致无法删除，请先让用户解绑");
        }

        LambdaQueryWrapper<SysDept> deptQueryWrapper = new LambdaQueryWrapper<>();
        deptQueryWrapper.eq(SysDept::getParentId, deptId);
        long deptCount = this.count(deptQueryWrapper);
        if (deptCount > 0) {
            throw new RuntimeException("该部门下还有子部门，请先删除子部门");
        }
        removeById(deptId);
    }
}

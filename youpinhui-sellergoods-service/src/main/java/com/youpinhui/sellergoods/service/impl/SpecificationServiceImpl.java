package com.youpinhui.sellergoods.service.impl;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.youpinhui.mapper.TbSpecificationMapper;
import com.youpinhui.mapper.TbSpecificationOptionMapper;
import com.youpinhui.pojo.TbSpecification;
import com.youpinhui.pojo.TbSpecificationExample;
import com.youpinhui.pojo.TbSpecificationExample.Criteria;
import com.youpinhui.pojo.TbSpecificationOption;
import com.youpinhui.pojo.TbSpecificationOptionExample;
import com.youpinhui.pojogroup.Specification;
import com.youpinhui.sellergoods.service.SpecificationService;

import entity.PageResult;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
@Transactional
public class SpecificationServiceImpl implements SpecificationService {

	@Autowired
	private TbSpecificationMapper specificationMapper;
	@Autowired
	private TbSpecificationOptionMapper specificationOptionMapper;
	/**
	 * 查询全部
	 */
	@Override
	public List<TbSpecification> findAll() {
		return specificationMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbSpecification> page=   (Page<TbSpecification>) specificationMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(Specification specification) {
		
		TbSpecification tbspecification = specification.getSpecification();
		specificationMapper.insert(tbspecification);
		
		List<TbSpecificationOption> specificationOptionList = specification.getSpecificationOptionList();
		for (TbSpecificationOption tso : specificationOptionList) {
			tso.setSpecId(tbspecification.getId());//设置规格id
			specificationOptionMapper.insert(tso);//新增规格
		}
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(Specification specification){
		//获取规格
		TbSpecification tbspecification = specification.getSpecification();
		specificationMapper.updateByPrimaryKey(tbspecification);
		
		
		//先删除原来的规格选项
		TbSpecificationOptionExample example=new TbSpecificationOptionExample();
		com.youpinhui.pojo.TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
		criteria.andSpecIdEqualTo(tbspecification.getId());
		specificationOptionMapper.deleteByExample(example);
		
		//获取规格选项列表
		List<TbSpecificationOption> specificationOptionList = specification.getSpecificationOptionList();
		for (TbSpecificationOption tso : specificationOptionList) {
			tso.setSpecId(tbspecification.getId());//设置规格id
			specificationOptionMapper.insert(tso);//新增规格
		}
		
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public Specification findOne(Long id){
		Specification specification = new Specification();
		//获取规格
		specification.setSpecification(specificationMapper.selectByPrimaryKey(id));
		
		
		//获取规格选项列表
		TbSpecificationOptionExample example=new TbSpecificationOptionExample();
		com.youpinhui.pojo.TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
		criteria.andSpecIdEqualTo(id);
		List<TbSpecificationOption> sepcificationList = specificationOptionMapper.selectByExample(example);
		
		specification.setSpecificationOptionList(sepcificationList);
		
		
		return specification;
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			//删除规格表数据
			specificationMapper.deleteByPrimaryKey(id);
			//删除规格选项表数据
			
			TbSpecificationOptionExample example=new TbSpecificationOptionExample();
			com.youpinhui.pojo.TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
			criteria.andSpecIdEqualTo(id);
			specificationOptionMapper.deleteByExample(example);
		}		
	}
	
	
		@Override
	public PageResult findPage(TbSpecification specification, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbSpecificationExample example=new TbSpecificationExample();
		Criteria criteria = example.createCriteria();
		
		if(specification!=null){			
						if(specification.getSpecName()!=null && specification.getSpecName().length()>0){
				criteria.andSpecNameLike("%"+specification.getSpecName()+"%");
			}
	
		}
		
		Page<TbSpecification> page= (Page<TbSpecification>)specificationMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}

		@Override
		public List<Map> selectOptionList() {
			// TODO Auto-generated method stub
			return specificationMapper.selectOptionList();
		}
	
}

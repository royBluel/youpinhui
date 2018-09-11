package com.youpinhui.sellergoods.service;
import java.util.List;
import com.youpinhui.pojo.TbGoods;
import com.youpinhui.pojo.TbItem;
import com.youpinhui.pojogroup.Goods;

import entity.PageResult;
/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface GoodsService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbGoods> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public PageResult findPage(int pageNum,int pageSize);
	
	
	/**
	 * 增加
	*/
	public void add(Goods goods);
	
	
	/**
	 * 修改
	 */
	public void update(Goods goods);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public Goods findOne(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long [] ids);

	/**
	 * 分页
	 * @param pageNum 当前页 码
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage(TbGoods goods, int pageNum,int pageSize);
	
	/**
	 * 更新商品状态
	 * @param ids
	 * @param status
	 */
	public void updateStatus(Long[] ids,String status);
	/**
	 * 设置商品上架或者下架
	 * @param ids
	 * @param status
	 */
	public void setMarketableStatus(Long[] ids,String status);
	
	/**
	 * 根据SPU的id集合和状态查询SKU列表
	 * @param goodsIds
	 * @param status
	 * @return
	 */
	public List<TbItem> findItemListByGoodsIdListAndStatus(Long[] goodsIds,String status);
}

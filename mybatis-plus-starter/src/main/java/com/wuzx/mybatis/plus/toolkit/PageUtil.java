package com.yh.mybatis.plus.toolkit;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yh.mybatis.plus.domain.PageParam;

import java.util.List;

/**
 * @author  wuzhixuan 2022/10/11 10:00
 * @version 1.0
 */
public final class PageUtil {

	private PageUtil() {
	}

	/**
	 * 根据 PageParam 生成一个 IPage 实例
	 * @param pageParam 分页参数
	 * @param <V> 返回的 Record 对象
	 * @return IPage<V>
	 */
	public static <V> IPage<V> prodPage(PageParam pageParam) {
		Page<V> page = new Page<>(pageParam.getPageNo(), pageParam.getPageSize());
		List<PageParam.Sort> sorts = pageParam.getSorts();
		for (PageParam.Sort sort : sorts) {
			OrderItem orderItem = sort.isAsc() ? OrderItem.asc(sort.getField()) : OrderItem.desc(sort.getField());
			page.addOrder(orderItem);
		}
		return page;
	}

}

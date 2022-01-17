package tr.bays.customdao;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;

public class CustomResult {
	private BooleanExpression booleanExpression;
	private OrderSpecifier<Long> order;
	public CustomResult() {
		super();
	}
	public CustomResult(BooleanExpression booleanExpression, OrderSpecifier<Long> order) {
		super();
		this.booleanExpression = booleanExpression;
		this.order = order;
	}
	public BooleanExpression getBooleanExpression() {
		return booleanExpression;
	}
	public void setBooleanExpression(BooleanExpression booleanExpression) {
		this.booleanExpression = booleanExpression;
	}
	public OrderSpecifier<Long> getOrder() {
		return order;
	}
	public void setOrder(OrderSpecifier<Long> order) {
		this.order = order;
	}
}

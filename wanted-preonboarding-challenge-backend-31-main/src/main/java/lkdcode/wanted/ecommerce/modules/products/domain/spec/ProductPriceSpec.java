package lkdcode.wanted.ecommerce.modules.products.domain.spec;

public class ProductPriceSpec {
    public static final int BASE_PRICE_INTEGER = 10;
    public static final int BASE_PRICE_FRACTION = 2;
    public static final int BASE_PRICE_PRECISION = BASE_PRICE_INTEGER + BASE_PRICE_FRACTION;
    public static final int BASE_PRICE_SCALE = BASE_PRICE_FRACTION;

    public static final int SALE_PRICE_INTEGER = 10;
    public static final int SALE_PRICE_FRACTION = 2;
    public static final int SALE_PRICE_PRECISION = SALE_PRICE_INTEGER + SALE_PRICE_FRACTION;
    public static final int SALE_PRICE_SCALE = BASE_PRICE_FRACTION;

    public static final int COST_PRICE_INTEGER = 10;
    public static final int COST_PRICE_FRACTION = 2;
    public static final int COST_PRICE_PRECISION = COST_PRICE_INTEGER + COST_PRICE_FRACTION;
    public static final int COST_PRICE_SCALE = COST_PRICE_FRACTION;

    public static final int CURRENCY_LENGTH = 3;

    public static final int TAX_RATE_INTEGER = 3;
    public static final int TAX_RATE_FRACTION = 2;
    public static final int TAX_RATE_PRECISION = TAX_RATE_INTEGER + TAX_RATE_FRACTION;
    public static final int TAX_RATE_SCALE = TAX_RATE_FRACTION;
}

package com.plm.product.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * chenwenhua
 * 2018\11\10
 */
@Entity
@Data
@Table(name = "product_info")
@DynamicUpdate
public class ProductInfo implements Serializable {
    private static final long serialVersionUID = 5141414397160094149L;
    @Id
    private String productId;

    private String productName;

    private BigDecimal productPrice;

    private Integer productStock;

    private String productDescription;

    private String productIcon;

    private Integer productStatus;

    private Integer categoryType;

    private Date createTime;

    private Date updateTime;
}

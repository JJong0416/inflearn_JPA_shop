package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookForm {

    private Long id; // 상품 변경을 위해서는 id가 필요하다.

    private String name;
    private int price;
    private int stockQuantity;

    private String author;
    private String isbn;
}

package com.omer.stockmanagement.productservice.exception.exceptions;

import com.omer.stockmanagement.productservice.enums.Language;
import com.omer.stockmanagement.productservice.exception.enums.IFriendlyMessageCode;
import com.omer.stockmanagement.productservice.exception.utils.FriendlyMessageUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class ProductNotCreatedException extends RuntimeException {

    private final Language language;
    private final IFriendlyMessageCode friendlyMessageCode;

    public ProductNotCreatedException(Language language, IFriendlyMessageCode friendlyMessageCode, String message) {
        super(FriendlyMessageUtils.getFriendlyMessage(language, friendlyMessageCode));
        this.friendlyMessageCode = friendlyMessageCode;
        this.language = language;
        log.error("[ProductNotCreatedException] -> message: {} developer message: {}", FriendlyMessageUtils.getFriendlyMessage(language, friendlyMessageCode), message);
    }

}

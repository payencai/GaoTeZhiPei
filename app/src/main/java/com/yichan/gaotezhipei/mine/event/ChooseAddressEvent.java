package com.yichan.gaotezhipei.mine.event;

import com.yichan.gaotezhipei.common.entity.ContactInformEntity;
import com.yichan.gaotezhipei.mine.entity.AddressItem;

/**
 * Created by ckerv on 2018/2/8.
 */

public class ChooseAddressEvent {

    public int chooseType;
    public AddressItem addressItem;
    public ContactInformEntity contactInformEntity;

    public ChooseAddressEvent(int chooseType, AddressItem addressItem) {
        this.chooseType = chooseType;
        this.addressItem = addressItem;
    }

    public ChooseAddressEvent(int chooseType, ContactInformEntity contactInformEntity) {
        this.chooseType = chooseType;
        this.contactInformEntity = contactInformEntity;
    }

}

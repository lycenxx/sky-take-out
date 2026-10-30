package com.sky.service;

import com.sky.entity.AddressBook;
import java.util.List;

/** 地址簿业务接口。 */
public interface AddressBookService {

    /** 按条件查询地址。 */
    List<AddressBook> list(AddressBook addressBook);

    /** 新增地址。 */
    void save(AddressBook addressBook);

    /** 按 ID 查询地址。 */
    AddressBook getById(Long id);

    /** 更新地址。 */
    void update(AddressBook addressBook);

    /** 设置默认地址。 */
    void setDefault(AddressBook addressBook);

    /** 按 ID 删除地址。 */
    void deleteById(Long id);

}

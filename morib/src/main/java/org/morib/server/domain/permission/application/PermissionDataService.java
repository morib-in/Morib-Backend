package org.morib.server.domain.permission.application;


/**
 * 추후 회의 후 DataService 같은 네이밍을 변경할 예정
 * 추가로 진행할
 */
public interface PermissionDataService {

    void save();

    void findById();

    void findAll();

    void deleteById();

    void deleteAll();

}

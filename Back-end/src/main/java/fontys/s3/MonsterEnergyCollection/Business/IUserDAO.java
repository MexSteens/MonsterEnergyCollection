package fontys.s3.MonsterEnergyCollection.Business;

import fontys.s3.MonsterEnergyCollection.Business.Entity.UserEntity;
import fontys.s3.MonsterEnergyCollection.Domain.SearchCriteria;

import java.util.List;

public interface IUserDAO {
    List<UserEntity> searchUser(List<SearchCriteria> params);

    void save(UserEntity entity);
}

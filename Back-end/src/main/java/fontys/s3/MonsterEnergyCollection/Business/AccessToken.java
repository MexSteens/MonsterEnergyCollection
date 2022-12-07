package fontys.s3.MonsterEnergyCollection.Business;

public interface AccessToken {
    String Encode(fontys.s3.MonsterEnergyCollection.Domain.AccessToken accessToken);

    fontys.s3.MonsterEnergyCollection.Domain.AccessToken Decode(String accessTokenEncoded);
}

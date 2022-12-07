package fontys.s3.MonsterEnergyCollection.Business;

public interface BruteForceProtectionService {
    Boolean LoginFailed(String ipAddress);

    void LoginSucceeded(String ipAddress);
}

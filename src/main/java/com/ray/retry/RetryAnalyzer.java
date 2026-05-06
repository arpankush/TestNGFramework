package com.ray.retry;

import com.ray.utils.readers.ConfigReader;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int attempt = 0;
    private static final int MAX_RETRY = Integer.parseInt(
            ConfigReader.get("retry.count")
    );

    @Override
    public boolean retry(ITestResult result){
        if (attempt < MAX_RETRY){
            attempt++;
            return true;
        }
        return false;
    }
}

package com.cxy.service;

import com.cxy.entry.Commit_info;
import com.cxy.entry.commitRequest;

public interface CommitService {
    Boolean add(String token, commitRequest commitRequest);

    Commit_info list(Long filmID);
}

package com.cxy.entry;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("em_commit")
public class Commit_info {
    @Id
    String _id;

    Commit[] commits = {};
}

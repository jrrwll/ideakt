package org.dreamcat.ideatk.get_start.task;

import lombok.Data;

/**
 * @author Jerry Will
 * @version 2024-02-16
 */
@Data
public class ComponentUpdateInfo {

    private String id;
    private String name;
    private String version;
    private String description;
    private String changeNotes;

    private long size;
    private long date;
    private boolean force; // 强制更新
    private String updateLogUrl; // 更新日志URL
    private String downloadUrl;
}

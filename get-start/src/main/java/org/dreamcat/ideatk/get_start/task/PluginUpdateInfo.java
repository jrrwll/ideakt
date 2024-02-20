package org.dreamcat.ideatk.get_start.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jerry Will
 * @version 2024-02-16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PluginUpdateInfo {

    private String name;
    private String version;
    private String description;
    private String sinceBuild;
    private String untilBuild;
    private String changeNotes;

    private long size;
    private long date;
    private boolean force; // 强制更新
    private boolean updatedWithoutRestart; // 更新是否需要重启
    private String updateLogUrl; // 更新日志URL
    private String downloadUrl;
}

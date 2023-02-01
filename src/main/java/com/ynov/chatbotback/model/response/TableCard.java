package com.ynov.chatbotback.model.response;

import java.util.List;
import lombok.Data;

@Data
public class TableCard {
    private String title;
    private String subtitle;
    private Image image;
    private List<ColumnProperties> columnProperties;
    private List<TableCardRow> rows;
}

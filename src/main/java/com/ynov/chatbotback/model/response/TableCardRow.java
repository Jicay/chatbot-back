package com.ynov.chatbotback.model.response;

import java.util.List;
import lombok.Data;

@Data
public class TableCardRow {
    private List<TableCardCell> cells;
}

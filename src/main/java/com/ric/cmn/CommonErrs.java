package com.ric.cmn;

/**
 * Коды ошибок
 */
public interface CommonErrs {
    // счетчик не существует
    long ERR_METER_NOT_FOUND = 0x1; // 0000 0001
    // объект не найден в базе Директ
    long ERR_OBJECT_NOT_FOUND = 0x2; // 0000 0010
    // разные KLSK на один адрес
    long ERR_DIFF_KLSK_BUT_SAME_ADDR = 0x4; // 0000 0100
    // найден пустой KLSK в данном адресе
    long ERR_EMPTY_KLSK = 0x8; // 0000 1000
    // не найден счетчик в доме по GUID
    long ERR_METER_NOT_FOUND_BY_GUID = 0x10; // 0001 0000
    // счетчик не привязан к Директ
    long ERR_METER_NOT_ASSOC_DIRECT = 0x20; // 0010 0000
    // счетчик не является актуальным в Директ
    long ERR_METER_NOT_ACTUAL_DIRECT = 0x40; // 0100 0000;
    // ошибка при записи в базу
    long ERROR_WHILE_SAVING_DATA = 0x80; // 1000 0000;
    // найден дубль FK_KLSK_OBJ в таблице Eolink
    long ERR_DOUBLE_KLSK_EOLINK = 0x100; // 1 0000 0000
    // объект лиц.счета EOLINK не найден в SCOTT.KART по LSK
    long ERR_LSK_NOT_FOUND = 0x200; // 10 0000 0000
    // объект лицевого счета EOLINK.ID имеет некорректную родительскую запись
    long ERR_INCORRECT_PARENT = 0x400; // 100 0000 0000
    // прочие ошибки импорта объектов в ГИС ЖКХ
    long ERR_IMPORT = 0x800; // 1000 0000 0000
    // найден пустой KLSK в данном адресе
    long ERR_NOT_FOUND_ACTUAL_OBJ = 0x1000; // 1 0000 0000 0000
    // не заполнено ФИО в KART лиц.счета
    long ERR_EMPTY_FIO = 0x2000; // 10 0000 0000 0000

    String ERR_MSG_METER_VAL_ZERO = "ОШИБКА! Нулевые новые показания!";
    String ERR_MSG_METER_VAL_LESS = "ОШИБКА! Новые показания меньше или равны существующим в базе!";
    String ERR_MSG_METER_NOT_FOUND = "ОШИБКА! Не найден счетчик!";
    String ERR_MSG_METER_VAL_TOO_BIG = "ОШИБКА! Переданы показания, вызывающие начисление слишком большого объема!";
    String ERR_MSG_METER_VAL_SUCCESS = "Отправлено в биллинг успешно!";
    String ERR_MSG_METER_VAL_OTHERS = "Прочие ошибки!";

}
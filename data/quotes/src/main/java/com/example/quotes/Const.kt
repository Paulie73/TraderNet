package com.example.quotes

object Const {
    const val CLIENT_API_BASE_URL = "https://tradernet.com/api"
    const val TRADING_API_BASE_URL = "wss.tradernet.com"
    val defaultIds = listOf(
        "SP500.IDX",
        "AAPL.US",
        "RSTI",
        "GAZP",
        "MRKZ",
        "RUAL",
        "HYDR",
        "MRKS",
        "SBER",
        "FEES",
        "TGKA",
        "VTBR",
        "ANH.US",
        "VICL.US",
        "BURG.US",
        "NBL.US",
        "YETI.US",
        "WSFS.US",
        "NIO.US",
        "DXC.US",
        "MIC.US",
        "HSBC.US",
        "EXPN.EU",
        "GSK.EU",
        "SHP.EU",
        "MAN.EU",
        "DB1.EU",
        "MUV2.EU",
        "TATE.EU",
        "KGF.EU",
        "MGGT.EU",
        "SGGD.EU"
    )
    const val LOAD_LOGO_URL_PATH = "https://tradernet.com/logos/get-logo-by-ticker?ticker="
}
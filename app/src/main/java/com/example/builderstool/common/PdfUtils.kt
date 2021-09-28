package com.example.builderstool.common


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.FileProvider
import com.example.builderstool.R
import com.example.builderstool.model.Order
import com.example.builderstool.model.Purchase
import com.itextpdf.text.*
import com.itextpdf.text.Rectangle
import com.itextpdf.text.pdf.BaseFont
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception
import java.text.DecimalFormat

class PdfUtils {
    private val currentUser by lazy { SharedPreferenceManager(BaseApplication.getInstance()).user }
    private val baseFont by lazy {
        BaseFont.createFont(
            "assets/argentum-extralight.ttf",
            BaseFont.IDENTITY_H,
            BaseFont.EMBEDDED
        )
    }
    private val bfBold12 by lazy {
        Font(Font.FontFamily.TIMES_ROMAN, 12F, Font.BOLD, BaseColor(0, 0, 0))
    }
    private val bfBold15 by lazy {
        Font(
            Font.FontFamily.TIMES_ROMAN,
            15F,
            Font.BOLD,
            BaseColor(0, 0, 0)
        )
    }
    private val bf12 by lazy { Font(Font.FontFamily.TIMES_ROMAN, 12F) }
    private var length = 0F
    private val decimalFormat by lazy { DecimalFormat(".00") }
    private val cf8 by lazy { Font(baseFont, 9F) }
    private val cfb8 by lazy { Font(baseFont, 9F, Font.BOLD, BaseColor(0, 0, 0)) }
    private val cfb10 by lazy { Font(baseFont, 10F, Font.BOLD, BaseColor(0, 0, 0)) }


    companion object {
        const val KITCHEN_BILL = "kitchen_bill"
        const val PURCHASE_BILL = "purchase_bill"
        private const val PD_BASE_PATH = "BuildersTool"
        private const val PD_DAILY_BILLS_PATH = "BuildersTool/PurchaseBills"
        private const val NOTIFICATION_CHANNEL_ID = "dailyNotification"
    }

    fun createPurchaseBill(
        context: Context,
        orderDetails: Purchase
    ) {
        length = 0F
        val rootPath = File(context.getExternalFilesDir(""),"")
        if (!rootPath.exists()) {
            rootPath.mkdir()
        }
        val newFile = File("$rootPath/Purchase-${orderDetails.id}.pdf")
        try {
            newFile.delete()
            newFile.createNewFile()
        } catch (e: IOException) {
            Toast.makeText(context, "File cannot be created", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
        if (newFile.exists()) {
            val documentList by lazy { mutableListOf<Element>() }
            try {
                val heading = Paragraph(currentUser?.name, cfb8)
                heading.alignment = Element.ALIGN_CENTER
                documentList.add(heading)
                length += 25F
                val paragraph = Paragraph("\n")
                if (!(currentUser?.mobile.equals("") || currentUser?.mobile == null)) {
                    val phoneNo = Paragraph(currentUser?.mobile, cfb8)
                    phoneNo.alignment = Element.ALIGN_CENTER
                    documentList.add(phoneNo)
                    length += 25F
                    documentList.add(paragraph)
                    length += 25F
                }
                val supplierName = orderDetails.supplierName

                val orderItems = orderDetails.products
                when (orderDetails.billType) {
//                    KITCHEN_BILL -> {
//                        val columnWidths2 = floatArrayOf(5f, 5f)
//                        val headerForTable = PdfPTable(columnWidths2)
//                        headerForTable.widthPercentage = 95f
//                        insertCellWithoutBorder(
//                            headerForTable,
//                            "Supplier : $supplierName",
//                            Element.ALIGN_LEFT,
//                            2,
//                            cf8
//                        )
//                        insertCellWithoutBorder(
//                            headerForTable,
//                            "Purchase Id : ${orderDetails.id}",
//                            Element.ALIGN_LEFT,
//                            2,
//                            cf8
//                        )
//                        documentList.add(headerForTable)
//                        length += 25F
//                        documentList.add(paragraph)
//                        length += 25F
//                        val columnWidths = floatArrayOf(3f, 6f, 3f)
//                        val table = PdfPTable(columnWidths)
//                        table.widthPercentage = 100f
//                        insertCell(table, "S.No", Element.ALIGN_CENTER, 1, cfb8)
//                        insertCell(table, "Item", Element.ALIGN_CENTER, 1, cfb8)
//                        insertCell(table, "Qty", Element.ALIGN_CENTER, 1, cfb8)
//                        table.headerRows = 1
//                        var i = 0
//                        orderItems?.size?.let { it ->
//                            while (i < it) {
//                                insertCell(table, (i + 1).toString(), Element.ALIGN_CENTER, 1, cf8)
//                                orderItems[i].itemName?.let {
//                                    insertCell(
//                                        table,
//                                        it,
//                                        Element.ALIGN_CENTER,
//                                        1,
//                                        cf8
//                                    )
//                                    insertCell(
//                                        table,
//                                        orderItems[i].quantity.toString(),
//                                        Element.ALIGN_CENTER,
//                                        1,
//                                        cf8
//                                    )
//                                    length += it.length * 1.85F
//                                }
//                                i++
//                            }
//                        }
//                        documentList.add(table)
//                    }
                    PURCHASE_BILL -> {
                        val billNo =
                            Paragraph("Purchase id : " + orderDetails.id, cfb8)
                        billNo.alignment = Element.ALIGN_LEFT
                        val supplierName =
                            Paragraph("Supplier : " + orderDetails.supplierName, cfb8)
                        billNo.alignment = Element.ALIGN_LEFT
                        supplierName.alignment = Element.ALIGN_LEFT
                        documentList.add(billNo)
                        documentList.add(supplierName)
                        length += 25F
                        documentList.add(paragraph)
                        length += 25F
                        val columnWidths1 = floatArrayOf(6.5f, 2.6f, 4.5f, 5.5f)
                        val table1 = PdfPTable(columnWidths1)
                        table1.widthPercentage = 100f
                        insertCell(table1, "Product name", Element.ALIGN_CENTER, 1, cfb8)
                        insertCell(table1, "Qty", Element.ALIGN_CENTER, 1, cfb8)
                        insertCell(table1, "Price", Element.ALIGN_CENTER, 1, cfb8)
                        insertCell(table1, "Amt", Element.ALIGN_CENTER, 1, cfb8)
                        table1.headerRows = 1
                        length += 25F
                        var i = 0
                        orderItems?.size?.let {
                            while (i < orderItems.size) {
                                val amount = orderItems[i].quantity !!.times( orderItems[i].price!!.toInt())
                                orderItems[i].name?.let {
                                    insertCell(
                                        table1,
                                        it,
                                        Element.ALIGN_CENTER,
                                        1,
                                        cf8
                                    )
                                    insertCell(
                                        table1,
                                        orderItems[i].quantity.toString(),
                                        Element.ALIGN_CENTER,
                                        1,
                                        cf8
                                    )
                                    insertCell(
                                        table1,
                                        decimalFormat.format(orderItems[i].price!!.toInt()),
                                        Element.ALIGN_CENTER,
                                        1,
                                        cf8
                                    )
                                    insertCell(
                                        table1,
                                        decimalFormat.format(amount),
                                        Element.ALIGN_CENTER,
                                        1,
                                        cf8
                                    )
                                    length += it.length * 1.85F
                                }
                                i++
                            }
                        }
                        documentList.add(table1)
                        val columnWidthsTotal = floatArrayOf(8f, 4.5f)
                        val tableTotal = PdfPTable(columnWidthsTotal)
                        val tablePaid = PdfPTable(columnWidthsTotal)
//                        if (orderDetails.discountApplied) {
//                            tableTotal.widthPercentage = 80f
//                            insertCellWithoutBorder(
//                                tableTotal,
//                                "Sub Total (\u20B9)",
//                                Element.ALIGN_RIGHT,
//                                1,
//                                cfb8
//                            )
//                            orderDetails.subTotal?.let {
//                                insertCell(
//                                    tableTotal,
//                                    it,
//                                    Element.ALIGN_RIGHT,
//                                    1,
//                                    cf8
//                                )
//                            }
//                            insertCellWithoutBorder(
//                                tableTotal,
//                                "Discount (\u20B9)",
//                                Element.ALIGN_RIGHT,
//                                1,
//                                cf8
//                            )
//                            orderDetails.discount?.let {
//                                insertCell(
//                                    tableTotal,
//                                    it,
//                                    Element.ALIGN_RIGHT,
//                                    1,
//                                    cf8
//                                )
//                            }
//                        }
                        insertCellWithoutBorder(
                            tableTotal,
                            "Total (\u20B9)",
                            Element.ALIGN_RIGHT,
                            1,
                            cfb8
                        )
                        orderDetails.total?.let {
                            insertCell(
                                tableTotal,
                                it.toString(),
                                Element.ALIGN_RIGHT,
                                1,
                                cf8
                            )
                        }
                        length += 25F
                        insertCellWithoutBorder(
                            tableTotal,
                            "Paid (\u20B9)",
                            Element.ALIGN_RIGHT,
                            1,
                            cfb8
                        )
                        orderDetails.paid?.let {
                            insertCell(
                                tableTotal,
                                it.toString(),
                                Element.ALIGN_RIGHT,
                                1,
                                cf8
                            )
                        }
                        length += 25F
                        insertCellWithoutBorder(
                            tableTotal,
                            "Balance (\u20B9)",
                            Element.ALIGN_RIGHT,
                            1,
                            cfb8
                        )
                        orderDetails.balance?.let {
                            insertCell(
                                tableTotal,
                                it.toString(),
                                Element.ALIGN_RIGHT,
                                1,
                                cf8
                            )
                        }

                        tableTotal.horizontalAlignment = Element.ALIGN_RIGHT
                        documentList.add(tableTotal)
                        length += 25F
                    }
                }
                documentList.add(paragraph)
                length += 25F
//                val footer = Paragraph("ThankYou for visiting " + currentUser?.name, cf8)
//                footer.alignment = Element.ALIGN_CENTER
//                documentList.add(footer)
//                length += 25F
                val footer1 = Paragraph("Visit Again!!", cf8)
                footer1.alignment = Element.ALIGN_CENTER
                documentList.add(footer1)
                length += 25F
                documentList.add(paragraph)
                val document = Document()
                PdfWriter.getInstance(
                    document,
                    FileOutputStream(newFile)
                )
                document.pageSize = Rectangle(164.41f, length)
//                document.pageSize = Rectangle(130.41f, length)
                document.setMargins(10F, 10F, 10F, 10F)
                document.open()
                for (element in documentList) {
                    document.add(element)
                }
                document.close()
                val uri = if (Build.VERSION.SDK_INT < 24) {
                    Uri.fromFile(newFile)
                } else {
                    FileProvider.getUriForFile(
                        context,
                        "com.example.builderstool",
                        newFile
                    )
//                    Uri.fromFile(newFile)

                }
                val intent = Intent()
                intent.action = Intent.ACTION_VIEW
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.setDataAndType(uri, "application/pdf")
                Toast.makeText(context,"Created",Toast.LENGTH_SHORT).show()
                if (intent.resolveActivity(context.packageManager) != null) {

                        context.startActivity(intent)

                }
            } catch (e: IOException) {
                Toast.makeText(context,e.message.toString(),Toast.LENGTH_SHORT).show()
                Log.e("PDF",e.message.toString())
                e.printStackTrace()
            } catch (e: DocumentException) {
                Toast.makeText(context,e.message.toString(),Toast.LENGTH_SHORT).show()
                Log.e("PDF",e.message.toString())
                e.printStackTrace()
            }catch (e:Exception){
                Toast.makeText(context,e.message.toString(),Toast.LENGTH_SHORT).show()
                Log.e("PDF",e.message.toString())
                e.printStackTrace()
            }
        }
    }

    fun createOrderBill(
        context: Context,
        orderDetails: Order
    ) {
        length = 0F
        val rootPath = File(context.getExternalFilesDir(""),"${orderDetails.customerName}")
        if (!rootPath.exists()) {
            rootPath.mkdir()
        }
        val newFile = File("$rootPath/Order-${orderDetails.id}.pdf")
        try {
            newFile.delete()
            newFile.createNewFile()
        } catch (e: IOException) {
            Toast.makeText(context, "File cannot be created", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
        if (newFile.exists()) {
            val documentList by lazy { mutableListOf<Element>() }
            try {
                val heading = Paragraph(currentUser?.name, cfb8)
                heading.alignment = Element.ALIGN_CENTER
                documentList.add(heading)
                length += 25F
                val paragraph = Paragraph("\n")
                if (!(currentUser?.mobile.equals("") || currentUser?.mobile == null)) {
                    val phoneNo = Paragraph(currentUser?.mobile, cfb8)
                    phoneNo.alignment = Element.ALIGN_CENTER
                    documentList.add(phoneNo)
                    length += 25F
                    documentList.add(paragraph)
                    length += 25F
                }
                val supplierName = orderDetails.customerId

                val orderItems = orderDetails.products
                when (orderDetails.billType) {
//                    KITCHEN_BILL -> {
//                        val columnWidths2 = floatArrayOf(5f, 5f)
//                        val headerForTable = PdfPTable(columnWidths2)
//                        headerForTable.widthPercentage = 95f
//                        insertCellWithoutBorder(
//                            headerForTable,
//                            "Supplier : $supplierName",
//                            Element.ALIGN_LEFT,
//                            2,
//                            cf8
//                        )
//                        insertCellWithoutBorder(
//                            headerForTable,
//                            "Purchase Id : ${orderDetails.id}",
//                            Element.ALIGN_LEFT,
//                            2,
//                            cf8
//                        )
//                        documentList.add(headerForTable)
//                        length += 25F
//                        documentList.add(paragraph)
//                        length += 25F
//                        val columnWidths = floatArrayOf(3f, 6f, 3f)
//                        val table = PdfPTable(columnWidths)
//                        table.widthPercentage = 100f
//                        insertCell(table, "S.No", Element.ALIGN_CENTER, 1, cfb8)
//                        insertCell(table, "Item", Element.ALIGN_CENTER, 1, cfb8)
//                        insertCell(table, "Qty", Element.ALIGN_CENTER, 1, cfb8)
//                        table.headerRows = 1
//                        var i = 0
//                        orderItems?.size?.let { it ->
//                            while (i < it) {
//                                insertCell(table, (i + 1).toString(), Element.ALIGN_CENTER, 1, cf8)
//                                orderItems[i].itemName?.let {
//                                    insertCell(
//                                        table,
//                                        it,
//                                        Element.ALIGN_CENTER,
//                                        1,
//                                        cf8
//                                    )
//                                    insertCell(
//                                        table,
//                                        orderItems[i].quantity.toString(),
//                                        Element.ALIGN_CENTER,
//                                        1,
//                                        cf8
//                                    )
//                                    length += it.length * 1.85F
//                                }
//                                i++
//                            }
//                        }
//                        documentList.add(table)
//                    }
                    PURCHASE_BILL -> {
                        val billNo =
                            Paragraph("Order id : " + orderDetails.id, cfb8)
                        billNo.alignment = Element.ALIGN_LEFT
                        val supplierName =
                            Paragraph("Site : " + orderDetails.customerName, cfb8)
                        billNo.alignment = Element.ALIGN_LEFT
                        supplierName.alignment = Element.ALIGN_LEFT
                        documentList.add(billNo)
                        documentList.add(supplierName)
                        length += 25F
                        documentList.add(paragraph)
                        length += 25F
                        val columnWidths1 = floatArrayOf(6.5f, 2.6f, 4.5f, 5.5f)
                        val table1 = PdfPTable(columnWidths1)
                        table1.widthPercentage = 100f
                        insertCell(table1, "Product name", Element.ALIGN_CENTER, 1, cfb8)
                        insertCell(table1, "Qty", Element.ALIGN_CENTER, 1, cfb8)
                        insertCell(table1, "Price", Element.ALIGN_CENTER, 1, cfb8)
                        insertCell(table1, "Amt", Element.ALIGN_CENTER, 1, cfb8)
                        table1.headerRows = 1
                        length += 25F
                        var i = 0
                        orderItems?.size?.let {
                            while (i < orderItems.size) {
                                val amount = orderItems[i].quantity !!.times( orderItems[i].price!!.toInt())
                                orderItems[i].name?.let {
                                    insertCell(
                                        table1,
                                        it,
                                        Element.ALIGN_CENTER,
                                        1,
                                        cf8
                                    )
                                    insertCell(
                                        table1,
                                        orderItems[i].quantity.toString(),
                                        Element.ALIGN_CENTER,
                                        1,
                                        cf8
                                    )
                                    insertCell(
                                        table1,
                                        decimalFormat.format(orderItems[i].price!!.toInt()),
                                        Element.ALIGN_CENTER,
                                        1,
                                        cf8
                                    )
                                    insertCell(
                                        table1,
                                        decimalFormat.format(amount),
                                        Element.ALIGN_CENTER,
                                        1,
                                        cf8
                                    )
                                    length += it.length * 1.85F
                                }
                                i++
                            }
                        }
                        documentList.add(table1)
                        val columnWidthsTotal = floatArrayOf(8f, 4.5f)
                        val tableTotal = PdfPTable(columnWidthsTotal)
                        val tablePaid = PdfPTable(columnWidthsTotal)
//                        if (orderDetails.discountApplied) {
//                            tableTotal.widthPercentage = 80f
//                            insertCellWithoutBorder(
//                                tableTotal,
//                                "Sub Total (\u20B9)",
//                                Element.ALIGN_RIGHT,
//                                1,
//                                cfb8
//                            )
//                            orderDetails.subTotal?.let {
//                                insertCell(
//                                    tableTotal,
//                                    it,
//                                    Element.ALIGN_RIGHT,
//                                    1,
//                                    cf8
//                                )
//                            }
//                            insertCellWithoutBorder(
//                                tableTotal,
//                                "Discount (\u20B9)",
//                                Element.ALIGN_RIGHT,
//                                1,
//                                cf8
//                            )
//                            orderDetails.discount?.let {
//                                insertCell(
//                                    tableTotal,
//                                    it,
//                                    Element.ALIGN_RIGHT,
//                                    1,
//                                    cf8
//                                )
//                            }
//                        }
                        insertCellWithoutBorder(
                            tableTotal,
                            "Total (\u20B9)",
                            Element.ALIGN_RIGHT,
                            1,
                            cfb8
                        )
                        orderDetails.total?.let {
                            insertCell(
                                tableTotal,
                                it.toString(),
                                Element.ALIGN_RIGHT,
                                1,
                                cf8
                            )
                        }
                        length += 25F
                        insertCellWithoutBorder(
                            tableTotal,
                            "Paid (\u20B9)",
                            Element.ALIGN_RIGHT,
                            1,
                            cfb8
                        )
                        orderDetails.paid?.let {
                            insertCell(
                                tableTotal,
                                it.toString(),
                                Element.ALIGN_RIGHT,
                                1,
                                cf8
                            )
                        }
                        length += 25F
                        insertCellWithoutBorder(
                            tableTotal,
                            "Balance (\u20B9)",
                            Element.ALIGN_RIGHT,
                            1,
                            cfb8
                        )
                        orderDetails.balance?.let {
                            insertCell(
                                tableTotal,
                                it.toString(),
                                Element.ALIGN_RIGHT,
                                1,
                                cf8
                            )
                        }

                        tableTotal.horizontalAlignment = Element.ALIGN_RIGHT
                        documentList.add(tableTotal)
                        length += 25F
                    }
                }
                documentList.add(paragraph)
                length += 25F
//                val footer = Paragraph("ThankYou for visiting " + currentUser?.name, cf8)
//                footer.alignment = Element.ALIGN_CENTER
//                documentList.add(footer)
//                length += 25F
                val footer1 = Paragraph("Visit Again!!", cf8)
                footer1.alignment = Element.ALIGN_CENTER
                documentList.add(footer1)
                length += 25F
                documentList.add(paragraph)
                val document = Document()
                PdfWriter.getInstance(
                    document,
                    FileOutputStream(newFile)
                )
                document.pageSize = Rectangle(164.41f, length)
//                document.pageSize = Rectangle(130.41f, length)
                document.setMargins(10F, 10F, 10F, 10F)
                document.open()
                for (element in documentList) {
                    document.add(element)
                }
                document.close()
                val uri = if (Build.VERSION.SDK_INT < 24) {
                    Uri.fromFile(newFile)
                } else {
                    FileProvider.getUriForFile(
                        context,
                        "com.example.builderstool",
                        newFile
                    )
//                    Uri.fromFile(newFile)

                }
                val intent = Intent()
                intent.action = Intent.ACTION_VIEW
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.setDataAndType(uri, "application/pdf")
                Toast.makeText(context,"Created",Toast.LENGTH_SHORT).show()
                if (intent.resolveActivity(context.packageManager) != null) {

                    context.startActivity(intent)

                }
            } catch (e: IOException) {
                Toast.makeText(context,e.message.toString(),Toast.LENGTH_SHORT).show()
                Log.e("PDF",e.message.toString())
                e.printStackTrace()
            } catch (e: DocumentException) {
                Toast.makeText(context,e.message.toString(),Toast.LENGTH_SHORT).show()
                Log.e("PDF",e.message.toString())
                e.printStackTrace()
            }catch (e:Exception){
                Toast.makeText(context,e.message.toString(),Toast.LENGTH_SHORT).show()
                Log.e("PDF",e.message.toString())
                e.printStackTrace()
            }
        }
    }

//    fun createDailySalesBill(
//        task: String,
//        context: Context,
//        summaryReport: LMSalesReportResponse,
//        date: String
//    ): Boolean? {
//        if (summaryReport.billDetails?.size == 0) {
//            showNoOrderNotification(context, date)
//            return false
//        }
//        val rootPath = File(Environment.getExternalStorageDirectory(), PD_DAILY_BILLS_PATH)
//        if (!rootPath.exists()) {
//            rootPath.mkdir()
//        }
//        val billName = "sales-report-$date.pdf"
//        val newFile = File("$rootPath/$billName")
//        try {
//            newFile.createNewFile()
//        } catch (e: IOException) {
//            sendNotification(
//                context,
//                Intent(
//                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
//                    Uri.parse("package:" + BuildConfig.APPLICATION_ID)
//                ),
//                context.getString(R.string.permission),
//                context.getString(R.string.enable_storage_permission),
//                2
//            )
//            e.printStackTrace()
//            return false
//        }
//        return if (newFile.exists()) {
//            try {
//                val document = Document()
//                PdfWriter.getInstance(
//                    document,
//                    FileOutputStream(newFile)
//                )
//                document.pageSize = PageSize.A4
//                document.setMargins(10F, 10F, 15F, 15F)
//                document.open()
//                val heading = Paragraph(currentUser?.company?.name, bfBold15)
//                heading.alignment = Element.ALIGN_CENTER
//                document.add(heading)
//                if (!(currentUser?.company?.phoneNo.equals("") || currentUser?.company?.phoneNo == null)) {
//                    val phoneNo = Paragraph(currentUser?.company?.phoneNo, bf12)
//                    phoneNo.alignment = Element.ALIGN_CENTER
//                    document.add(phoneNo)
//                }
//                document.add(Paragraph("\n"))
//                val billDate = Paragraph("Sales summary report for $date", bfBold12)
//                billDate.alignment = Element.ALIGN_CENTER
//                document.add(billDate)
//                document.add(Paragraph("\n"))
//                val orderColumnWidths = floatArrayOf(5f, 5f)
//                val orderDetailsTable = PdfPTable(orderColumnWidths)
//                insertCell(orderDetailsTable, "Sales Details", Element.ALIGN_CENTER, 2, bfBold12)
//                insertCell(orderDetailsTable, "Total sales", Element.ALIGN_CENTER, 1, bf12)
//                insertCell(
//                    orderDetailsTable,
//                    "\u20B9 " + decimalFormat.format(
//                        summaryReport.orderDetails?.get("total_amount")?.toFloat()?.toDouble()
//                    ),
//                    Element.ALIGN_CENTER,
//                    1,
//                    cfb10
//                )
//                insertCell(orderDetailsTable, "Total no of order", Element.ALIGN_CENTER, 1, bf12)
//                summaryReport.orderDetails?.get("no_of_orders")?.let {
//                    insertCell(
//                        orderDetailsTable,
//                        it,
//                        Element.ALIGN_CENTER,
//                        1,
//                        cfb10
//                    )
//                }
//                insertCell(
//                    orderDetailsTable,
//                    "Total no of items sold",
//                    Element.ALIGN_CENTER,
//                    1,
//                    bf12
//                )
//                summaryReport.orderDetails?.get("no_of_items_sold")?.let {
//                    insertCell(
//                        orderDetailsTable,
//                        it,
//                        Element.ALIGN_CENTER,
//                        1,
//                        cfb10
//                    )
//                }
//                document.add(orderDetailsTable)
//                document.add(Paragraph("\n"))
//                val billColumnWidths = floatArrayOf(5f, 5f)
//                val billTable = PdfPTable(billColumnWidths)
//                billTable.widthPercentage = 80f
//                insertCell(billTable, "Bill Wise Sales Details", Element.ALIGN_CENTER, 2, bfBold12)
//                insertCell(billTable, "Bill No", Element.ALIGN_CENTER, 1, bfBold12)
//                insertCell(billTable, "Amount", Element.ALIGN_CENTER, 1, bfBold12)
//                billTable.headerRows = 1
//                summaryReport.billDetails?.keys?.let {
//                    for (key in it) {
//                        insertCell(billTable, key, Element.ALIGN_CENTER, 1, bf12)
//                        insertCell(
//                            billTable,
//                            "\u20B9 " + decimalFormat.format(
//                                summaryReport.billDetails?.get(key)?.toFloat()?.toDouble()
//                            ),
//                            Element.ALIGN_CENTER,
//                            1,
//                            cfb10
//                        )
//                    }
//                }
//
//                document.add(billTable)
//                document.add(Paragraph("\n"))
//                val countColumnWidths = floatArrayOf(5f, 5f)
//                val countTable = PdfPTable(countColumnWidths)
//                countTable.widthPercentage = 80f
//                insertCell(countTable, "Item Wise Sales Details", Element.ALIGN_CENTER, 2, bfBold12)
//                insertCell(countTable, "Item", Element.ALIGN_CENTER, 1, bfBold12)
//                insertCell(countTable, "Order Count", Element.ALIGN_CENTER, 1, bfBold12)
//                countTable.headerRows = 1
//                summaryReport.itemsCount?.keys?.let { it ->
//                    for (key in it) {
//                        insertCell(countTable, key, Element.ALIGN_CENTER, 1, bf12)
//                        summaryReport.itemsCount?.get(key)?.let {
//                            insertCell(
//                                countTable,
//                                it,
//                                Element.ALIGN_CENTER,
//                                1,
//                                cfb10
//                            )
//                        }
//                    }
//                }
//                document.add(countTable)
//                document.add(Paragraph("\n"))
//                val categoryColumnWidth = floatArrayOf(5f, 5f)
//                val typeTable = PdfPTable(categoryColumnWidth)
//                typeTable.widthPercentage = 80f
//                insertCell(typeTable, "Type Wise Sales Details", Element.ALIGN_CENTER, 2, bfBold12)
//                insertCell(typeTable, "Type Name", Element.ALIGN_CENTER, 1, bfBold12)
//                insertCell(typeTable, "Order Count", Element.ALIGN_CENTER, 1, bfBold12)
//                typeTable.headerRows = 1
//                summaryReport.typeCount?.keys?.let { it ->
//                    for (key in it) {
//                        insertCell(typeTable, key, Element.ALIGN_CENTER, 1, bf12)
//                        summaryReport.typeCount?.get(key)?.let {
//                            insertCell(
//                                typeTable,
//                                it,
//                                Element.ALIGN_CENTER,
//                                1,
//                                cfb10
//                            )
//                        }
//
//                    }
//                }
//                document.add(typeTable)
//                document.add(Paragraph("\n"))
//                val typeColumnWidth = floatArrayOf(5f, 5f)
//                val categoryTable = PdfPTable(typeColumnWidth)
//                typeTable.widthPercentage = 80f
//                insertCell(
//                    categoryTable,
//                    "Category Wise Sales Details",
//                    Element.ALIGN_CENTER,
//                    2,
//                    bfBold12
//                )
//                insertCell(categoryTable, "Category Name", Element.ALIGN_CENTER, 1, bfBold12)
//                insertCell(categoryTable, "Order Count", Element.ALIGN_CENTER, 1, bfBold12)
//                typeTable.headerRows = 1
//                summaryReport.categoryCount?.keys?.let { it ->
//                    for (key in it) {
//                        insertCell(categoryTable, key, Element.ALIGN_CENTER, 1, bf12)
//                        summaryReport.categoryCount?.get(key)?.let {
//                            insertCell(
//                                categoryTable,
//                                it,
//                                Element.ALIGN_CENTER,
//                                1,
//                                cfb10
//                            )
//                        }
//                    }
//                }
//                document.add(categoryTable)
//                document.close()
//                handleIntent(context, newFile, task, date)
//                true
//            } catch (e: IOException) {
//                e.printStackTrace()
//                false
//            } catch (e: DocumentException) {
//                e.printStackTrace()
//                false
//            } catch (e: Exception) {
//                e.printStackTrace()
//                false
//            }
//        } else {
//            false
//        }
//    }

    private fun handleIntent(
        context: Context,
        newFile: File?,
        task: String,
        date: String
    ) {
        val uri = if (Build.VERSION.SDK_INT < 24) {
            Uri.fromFile(newFile)
        } else {
            newFile?.let {
                FileProvider.getUriForFile(context, "com.layorz.mealee.fileprovider", it)
            }
        }
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.setDataAndType(uri, "application/pdf")
//        if (task.contains("notify")) {
//            sendNotification(
//                context,
//                intent,
//                context.getString(R.string.daily_report),
//                context.getString(R.string.report_ready, date),
//                1
//            )
//        }
        if (task.contains("open")) {
            context.startActivity(intent)
        }
    }

    private fun insertCell(
        table: PdfPTable,
        text: String,
        align: Int,
        colSpan: Int,
        font: Font?
    ) {
        val cell = PdfPCell(Phrase(text.trim { it <= ' ' }, font))
        cell.horizontalAlignment = align
        cell.colspan = colSpan
        cell.paddingTop = 3f
        cell.paddingBottom = 3f
        if (text.trim { it <= ' ' }.equals("", ignoreCase = true)) {
            cell.minimumHeight = 10f
        }
        table.addCell(cell)
    }

    private fun insertCellWithoutBorder(
        table: PdfPTable,
        text: String,
        align: Int,
        colSpan: Int,
        font: Font?
    ) {
        val cell = PdfPCell(Paragraph(text.trim { it <= ' ' }, font))
        cell.horizontalAlignment = align
        cell.colspan = colSpan
        cell.paddingRight = 5f
        if (text.trim { it <= ' ' }.equals("", ignoreCase = true)) {
            cell.minimumHeight = 10f
        }
        cell.border = Rectangle.NO_BORDER
        table.addCell(cell)
    }

//    private fun sendNotification(
//        context: Context,
//        intent: Intent,
//        title: String,
//        text: String,
//        id: Int
//    ) {
//        val notificationManager =
//            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val notificationChannel = NotificationChannel(
//                NOTIFICATION_CHANNEL_ID,
//                "dailyNotification",
//                NotificationManager.IMPORTANCE_HIGH
//            )
//            notificationChannel.description = "Sample Channel description"
//            notificationChannel.enableLights(true)
//            notificationChannel.lightColor = Color.RED
//            notificationChannel.vibrationPattern = longArrayOf(0, 1000, 500, 1000)
//            notificationChannel.enableVibration(true)
//            notificationManager.createNotificationChannel(notificationChannel)
//        }
//        val pendingIntent = PendingIntent.getActivity(
//            context, 0 /* Request code */, intent,
//            PendingIntent.FLAG_ONE_SHOT
//        )
//        val notificationBuilder =
//            NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
//        notificationBuilder.setAutoCancel(true)
//            .setDefaults(Notification.DEFAULT_ALL)
//            .setWhen(System.currentTimeMillis())
//            .setSmallIcon(R.drawable.ic_logo)
//            .setTicker("Daily Bill")
//            .setContentTitle(title)
//            .setContentText(text)
//            .setContentInfo("Information")
//            .setContentIntent(pendingIntent)
//            .addAction(R.drawable.ic_logo, "View Report", pendingIntent)
//        notificationManager.notify(id, notificationBuilder.build())
//    }
//
//    private fun showNoOrderNotification(context: Context, date: String) {
//        val notificationManager =
//            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val notificationChannel = NotificationChannel(
//                NOTIFICATION_CHANNEL_ID,
//                "dailyNotification",
//                NotificationManager.IMPORTANCE_HIGH
//            )
//            notificationChannel.description = "Daily Notification Creation"
//            notificationChannel.enableLights(true)
//            notificationChannel.lightColor = Color.RED
//            notificationChannel.vibrationPattern = longArrayOf(0, 1000, 500, 1000)
//            notificationChannel.enableVibration(true)
//            notificationManager.createNotificationChannel(notificationChannel)
//        }
//        val notificationBuilder =
//            NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
//        notificationBuilder.setAutoCancel(true)
//            .setDefaults(Notification.DEFAULT_ALL)
//            .setWhen(System.currentTimeMillis())
//            .setSmallIcon(R.drawable.ic_logo)
//            .setTicker("Daily Bill")
//            .setContentTitle(context.getString(R.string.daily_report))
//            .setContentText(context.getString(R.string.no_report_data, date))
//            .setContentInfo(context.getString(R.string.info))
//        notificationManager.notify(2, notificationBuilder.build())
//    }
}
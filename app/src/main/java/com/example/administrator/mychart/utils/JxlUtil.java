package com.example.administrator.mychart.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import android.os.Environment;

import com.example.administrator.mychart.model.Message;


public class JxlUtil {

	/**
	 * 导出生成excel文件，存放于SD卡中
	 * @author smart *
	 */
	private List<Message> list;
	
	public JxlUtil(List<Message> list){
		this.list = list;
	}



	public boolean toExcel() {
		// 准备设置excel工作表的标题
		String[] title = { "编号", "用户名", "昵称" };
		try {
			// 获得开始时间
			long start = System.currentTimeMillis();


			//判断SD卡是否存在
			if(!Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)){
				return false;
			}

			String SDdir =  Environment.getExternalStorageDirectory().toString();  //获取SD卡的根目录
			// 创建Excel工作薄
			WritableWorkbook wwb;
			// 在SD卡中，新建立一个名为person的jxl文件
			wwb = Workbook.createWorkbook(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/mychart_conversation.xls"));
			// 添加第一个工作表并设置第一个Sheet的名字
			WritableSheet sheet = wwb.createSheet("员工清单", 0);
			Label label;
			for (int i = 0; i < title.length; i++) {
				label = new Label(i, 0, title[i]);
				// 将定义好的单元格添加到工作表中
				sheet.addCell(label);
			}
			/*
			 * 保存数字到单元格，需要使用jxl.write.Number 必须使用其完整路径，否则会出现错误
			 */
			for(int i = 0 ; i < list.size(); i++){
				Message message = list.get(i);
				//添加编号
				jxl.write.Number number = new jxl.write.Number(0, i+1, message.getId());
				sheet.addCell(number);
				//添加姓名
				label = new Label(1,i+1,message.getSendUser().getUsername());
				sheet.addCell(label);
				//添加性别
				label = new Label(2,i+1,message.getSendUser().getNickname());
				sheet.addCell(label);
			}

			wwb.write(); //写入数据
			wwb.close(); //关闭文件


		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

}

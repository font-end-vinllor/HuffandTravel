package maze;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test {
	@SuppressWarnings("deprecation")
	public static void clear() throws AWTException
    {
		Robot r = new Robot();
        r.mousePress(InputEvent.BUTTON3_MASK);       // 按下鼠标右键
        r.mouseRelease(InputEvent.BUTTON3_MASK);    // 释放鼠标右键
        r.keyPress(KeyEvent.VK_CONTROL);             // 按下Ctrl键
        r.keyPress(KeyEvent.VK_R);                    // 按下R键
        r.keyRelease(KeyEvent.VK_R);                  // 释放R键
        r.keyRelease(KeyEvent.VK_CONTROL);            // 释放Ctrl键
        r.delay(100);       
    }

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		Create.createGraph("travel.sc", "path.sc");
		Path path = new Path();
		path.print();
		System.out.println("             -----------CONTENT-----------\n");
		System.out.println("              ---------0    增加景点---------\n");
		System.out.println("              ---------1     删除景点---------\n");
		System.out.println("              ---------2     修改景点---------\n");
		System.out.println("              ---------3     查询景点---------\n");
		System.out.println("              ---------4     增加路径---------\n");
		System.out.println("              ---------5     删除路径---------\n");
		System.out.println("              ---------6     修改路径---------\n");
		System.out.println("              ---------7     查询路径---------\n");
		System.out.print("请输入选择 : ");
		int choice = sc.nextInt();
		while(choice != -1) {
		switch (choice) {
		case 0: {
			String name = sc.next();
			System.out.println("景点是否增加成功 :"+path.addView(name));
			break;
		}
		case 1: {
			String name = sc.next();
			System.out.println("景点是否删除成功 : "+path.removeView(name));
			break;
		}
		case 2: {
			String aim = sc.next();
			String s = sc.next();
			System.out.println("景点是否修改成功:"+path.modifyView(aim, s));
			break;
		}
		case 3: {
			String s = sc.next();
			path.queryView(s);
			break;
		}
		case 4: {
			String start = sc.next();
			String end = sc.next();
			int weight = sc.nextInt();
			System.out.println("路径是否添加成功 :"+path.addPath(start, end, weight));
			break;

		}
		case 5: {
			String h = sc.nextLine();
			String name = sc.nextLine();
			System.out.println("路径是否删除成功 :"+path.removePath(name));
			break;
		}
		case 6:{
			String h = sc.nextLine();
			String aim = sc.nextLine();
			String s = sc.nextLine();
			System.out.println(" 路径是否修改成功 : "+path.modifyPath(aim, s));
			break;
		}
		case 7:{
			List<Integer> pat = new ArrayList<>();
//			String start = sc.next();
//			String end = sc.next();
			int start = sc.nextInt();
			int end  =sc.nextInt();
			path.queryPath(start, end, pat);
			if(pat.size() == 0) System.out.println(start+"不能到达"+end);
			else 
				for (int i = pat.size() - 1; i >= 0; i--)
				System.out.print(Create.vexs.get(pat.get(i)).name + "-----");
			System.out.println("\n");
		}
		}
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e1) {
		}
		try {
			clear();
		} catch (AWTException e) {
		}
		path.print();
		System.out.println("             -----------CONTENT-----------\n");
		System.out.println("              ------- -1     退出------------\n");
		System.out.println("              ---------0    增加景点---------\n");
		System.out.println("              ---------1     删除景点---------\n");
		System.out.println("              ---------2     修改景点---------\n");
		System.out.println("              ---------3     查询景点---------\n");
		System.out.println("              ---------4     增加路径---------\n");
		System.out.println("              ---------5     删除路径---------\n");
		System.out.println("              ---------6     修改路径---------\n");
		System.out.println("              ---------7     查询路径---------\n");
		System.out.print("请输入选择 : ");
		choice = sc.nextInt();
		}
		sc.close();
	}

}

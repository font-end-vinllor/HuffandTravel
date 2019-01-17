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
        r.mousePress(InputEvent.BUTTON3_MASK);       // ��������Ҽ�
        r.mouseRelease(InputEvent.BUTTON3_MASK);    // �ͷ�����Ҽ�
        r.keyPress(KeyEvent.VK_CONTROL);             // ����Ctrl��
        r.keyPress(KeyEvent.VK_R);                    // ����R��
        r.keyRelease(KeyEvent.VK_R);                  // �ͷ�R��
        r.keyRelease(KeyEvent.VK_CONTROL);            // �ͷ�Ctrl��
        r.delay(100);       
    }

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		Create.createGraph("travel.sc", "path.sc");
		Path path = new Path();
		path.print();
		System.out.println("             -----------CONTENT-----------\n");
		System.out.println("              ---------0    ���Ӿ���---------\n");
		System.out.println("              ---------1     ɾ������---------\n");
		System.out.println("              ---------2     �޸ľ���---------\n");
		System.out.println("              ---------3     ��ѯ����---------\n");
		System.out.println("              ---------4     ����·��---------\n");
		System.out.println("              ---------5     ɾ��·��---------\n");
		System.out.println("              ---------6     �޸�·��---------\n");
		System.out.println("              ---------7     ��ѯ·��---------\n");
		System.out.print("������ѡ�� : ");
		int choice = sc.nextInt();
		while(choice != -1) {
		switch (choice) {
		case 0: {
			String name = sc.next();
			System.out.println("�����Ƿ����ӳɹ� :"+path.addView(name));
			break;
		}
		case 1: {
			String name = sc.next();
			System.out.println("�����Ƿ�ɾ���ɹ� : "+path.removeView(name));
			break;
		}
		case 2: {
			String aim = sc.next();
			String s = sc.next();
			System.out.println("�����Ƿ��޸ĳɹ�:"+path.modifyView(aim, s));
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
			System.out.println("·���Ƿ���ӳɹ� :"+path.addPath(start, end, weight));
			break;

		}
		case 5: {
			String h = sc.nextLine();
			String name = sc.nextLine();
			System.out.println("·���Ƿ�ɾ���ɹ� :"+path.removePath(name));
			break;
		}
		case 6:{
			String h = sc.nextLine();
			String aim = sc.nextLine();
			String s = sc.nextLine();
			System.out.println(" ·���Ƿ��޸ĳɹ� : "+path.modifyPath(aim, s));
			break;
		}
		case 7:{
			List<Integer> pat = new ArrayList<>();
//			String start = sc.next();
//			String end = sc.next();
			int start = sc.nextInt();
			int end  =sc.nextInt();
			path.queryPath(start, end, pat);
			if(pat.size() == 0) System.out.println(start+"���ܵ���"+end);
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
		System.out.println("              ------- -1     �˳�------------\n");
		System.out.println("              ---------0    ���Ӿ���---------\n");
		System.out.println("              ---------1     ɾ������---------\n");
		System.out.println("              ---------2     �޸ľ���---------\n");
		System.out.println("              ---------3     ��ѯ����---------\n");
		System.out.println("              ---------4     ����·��---------\n");
		System.out.println("              ---------5     ɾ��·��---------\n");
		System.out.println("              ---------6     �޸�·��---------\n");
		System.out.println("              ---------7     ��ѯ·��---------\n");
		System.out.print("������ѡ�� : ");
		choice = sc.nextInt();
		}
		sc.close();
	}

}

package com.greenorbs.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;


/**
 * 
 * @author liangxing
 * 
 */
public class NetWorkTool {
	
	public static void main(String[] args) {
		System.out.println(NetWorkTool.getMAC());
		System.out.println(NetWorkTool.getLocalIp());
	}
	/**
	 * 获取当前操作系统名称.<br>
	 * 例如:windows xp,windows 7,linux 等.
	 * 
	 * @return 操作系统名称
	 */
	public static String getOSName() {
		return System.getProperty("os.name").toLowerCase();
	}

	/**
	 * 获取本机mac地址
	 * 
	 * @return mac address
	 */
	public static String getMAC() {
		String mac = null;
		String os = getOSName();
		if (os.equals("windows 7")) {// win7
			mac = getMAC_Win7();
		} else if (os.startsWith("windows")) {// xp
			mac = getMAC_Windows();
		} else {// unix
			mac = getMAC_Unix();
		}
		return mac;
	}

	/**
	 * 获取unix网卡的mac地址.<br>
	 * 非windows的系统默认调用本方法获取. <br>
	 * 如果有特殊系统请继续扩充新的取mac地址方法.<br>
	 * 
	 * @return mac地址
	 */
	private static String getMAC_Unix() {
		String mac = null;
		BufferedReader br = null;
		Process process = null;
		try {
			// linux下的命令，一般取eth0作为本地主网卡
			process = Runtime.getRuntime().exec("ifconfig eth0");
			// 显示信息中包含有mac地址信息
			br = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			String line = null;
			int index = -1;
			while ((line = br.readLine().trim()) != null) {
				// 寻找标示字符串[Link encap]
				index = line.toUpperCase().indexOf("LINK ENCAP");
				if (index >= 0) {// 找到了
					// 取出mac地址
					int len = line.length();
					mac = line.toUpperCase().substring(len - 18, len).trim();
					break;
				}
			}
		} catch (IOException e) {
			
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (process != null) {
					process.destroy();
				}
			} catch (Exception e) {
				
			}
		}
		return mac;
	}

	/**
	 * 获取widnows网卡的mac地址.
	 * 
	 * @return mac地址
	 */
	private static String getMAC_Windows() {
		String mac = null;
		BufferedReader br = null;
		Process process = null;
		try {
			// windows下的命令，显示信息中包含有mac地址信息
			process = Runtime.getRuntime().exec("getmac");
			br = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			String line = null;
			int index = -1;
			while ((line = br.readLine()) != null) {
				
				// 寻找标示字符串[physical
				line.contains("Device\\Tcpip");

				if (line.contains("Device\\Tcpip")) {// 找到了
					// 取出mac地址并去除2边空格
					mac = line.substring(0,17).trim().toUpperCase();
					break;
				}
				
			}
		} catch (IOException e) {
			
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				process = null;
			} catch (IOException e1) {
				
			}
		}

		return mac;
	}

	/**
	 * windows 7 专用 获取MAC地址
	 * 
	 * @return
	 * @throws Exception
	 */
	private static String getMAC_Win7() {

		String mac = null;
		StringBuffer sb = new StringBuffer();
		try {
			// 获取本地IP对象
			InetAddress ia = InetAddress.getLocalHost();
			// 获得网络接口对象（即网卡）
			byte[] macs = NetworkInterface.getByInetAddress(ia)
					.getHardwareAddress();
			for (int i = 0; i < macs.length; i++) {
				if (i != 0) {
					sb.append("-");
				}
				// macs[i] & 0xFF 是为了把byte转化为正整数
				String s = Integer.toHexString(macs[i] & 0xFF);
				sb.append(s.length() == 1 ? 0 + s : s);
			}
			mac = sb.toString().toUpperCase();
		} catch (SocketException e) {
			
		} catch (UnknownHostException e) {
			
		}
		return mac;
	}

	/**
	 * 获取本机内网IP
	 * 
	 * @return Ip
	 */
	public static String getLocalIp() {
		String localIp = null;// 本地IP

		try {
			Enumeration<NetworkInterface> netInterfaces = NetworkInterface
					.getNetworkInterfaces();
			InetAddress ip = null;
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface ni = netInterfaces.nextElement();
				Enumeration<InetAddress> address = ni.getInetAddresses();
				while (address.hasMoreElements()) {
					ip = address.nextElement();
					if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
							&& ip.getHostAddress().indexOf(":") == -1) {
						localIp = ip.getHostAddress();
					}
				}
			}
		} catch (SocketException e) {
			
		}
		return localIp;
	}

}

package HtmlCreate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

/**
* FTPファイル送信サンプル
* PASVモードでの接続を試み、PASVモードが使用できない場合は、
* ACTIVEモードでファイルを送信
*
* @version 1.0
*/
public class filetransport {
	public static byte[] localHostAddress;
	public static PrintWriter ctrlOutput;
	public static BufferedReader ctrlInput;

	public void filetransport(String h1) {
final int CTRLPORT = 21; // ftpの制御用のポート
Socket ctrlSocket = null; // 制御用ソケット



String hostName = "hostName"; // ホスト名
String userName = "userName"; // ユーザ名
String password = "password"; // パスワード
String dirName = "dirName"; // ファイル転送先ディレクトリ
String fileName = "temp"; // 転送ファイル
String storFileName = "ftptest.txt"; // 転送後のファイル名

System.out.println("hostName=" + hostName);
System.out.println("loginName=" + userName);
System.out.println("password=" + password);
System.out.println("dirName=" + dirName);
System.out.println("fileName=" + fileName);
System.out.println("storFileName=" + storFileName);

// 接続
try {
	ctrlSocket = new Socket(hostName, CTRLPORT);
} catch (UnknownHostException e) {
	// TODO 自動生成された catch ブロック
	e.printStackTrace();
} catch (IOException e) {
	// TODO 自動生成された catch ブロック
	e.printStackTrace();
}
localHostAddress = ctrlSocket.getLocalAddress().getAddress();
try {
	ctrlOutput =
	new PrintWriter(
	new BufferedWriter(
	new OutputStreamWriter(
	ctrlSocket.getOutputStream(), "JIS")),
	true);
} catch (UnsupportedEncodingException e) {
	// TODO 自動生成された catch ブロック
	e.printStackTrace();
} catch (IOException e) {
	// TODO 自動生成された catch ブロック
	e.printStackTrace();
}
try {
	ctrlInput =
	new BufferedReader(
	new InputStreamReader(ctrlSocket.getInputStream()));
} catch (IOException e) {
	// TODO 自動生成された catch ブロック
	e.printStackTrace();
}

String line = null;
try {
	line = ctrlInput.readLine();
} catch (IOException e) {
	// TODO 自動生成された catch ブロック
	e.printStackTrace();
}
while (line.charAt(3) == '-') {
try {
	line = ctrlInput.readLine();
} catch (IOException e) {
	// TODO 自動生成された catch ブロック
	e.printStackTrace();
}
}

// ユーザー認証
ctrlOutput.println("USER " + userName);
ctrlOutput.flush();
try {
	line = ctrlInput.readLine();
} catch (IOException e) {
	// TODO 自動生成された catch ブロック
	e.printStackTrace();
}
while (line.charAt(3) == '-') {
System.out.println("USER line=" + line);
try {
	line = ctrlInput.readLine();
} catch (IOException e) {
	// TODO 自動生成された catch ブロック
	e.printStackTrace();
}
}

ctrlOutput.println("PASS " + password);
ctrlOutput.flush();
try {
	line = ctrlInput.readLine();
} catch (IOException e) {
	// TODO 自動生成された catch ブロック
	e.printStackTrace();
}
while (line.charAt(3) == '-') {
System.out.println("PASS line=" + line);
try {
	line = ctrlInput.readLine();
} catch (IOException e) {
	// TODO 自動生成された catch ブロック
	e.printStackTrace();
}
}

// 指定したディレクトリに移動
ctrlOutput.println("CWD " + dirName);
ctrlOutput.flush();
try {
	line = ctrlInput.readLine();
} catch (IOException e) {
	// TODO 自動生成された catch ブロック
	e.printStackTrace();
}
while (line.charAt(3) == '-') {
System.out.println("CWD line=" + line);
try {
	line = ctrlInput.readLine();
} catch (IOException e) {
	// TODO 自動生成された catch ブロック
	e.printStackTrace();
}
}

// バイナリモードを設定(アスキーモードの場合は'TYPE A')
ctrlOutput.println("TYPE I");
ctrlOutput.flush();
try {
	line = ctrlInput.readLine();
} catch (IOException e) {
	// TODO 自動生成された catch ブロック
	e.printStackTrace();
}
while (line.charAt(3) == '-') {
System.out.println("TYPE line=" + line);
try {
	line = ctrlInput.readLine();
} catch (IOException e) {
	// TODO 自動生成された catch ブロック
	e.printStackTrace();
}
}

// PASVモードテスト
ctrlOutput.println("PASV");
ctrlOutput.flush();
try {
	line = ctrlInput.readLine();
} catch (IOException e) {
	// TODO 自動生成された catch ブロック
	e.printStackTrace();
}
while (line.charAt(3) == '-') {
System.out.println("PASV line=" + line);
try {
	line = ctrlInput.readLine();
} catch (IOException e) {
	// TODO 自動生成された catch ブロック
	e.printStackTrace();
}
}

// PASVモード可の場合
if (Integer.parseInt(line.substring(0, 3)) == 227) {
System.out.println("PASVモード");
int port;
String ip;
int[] numbers = new int[6];
String temp;
StringTokenizer tempToken;
tempToken = new StringTokenizer(line, "(");
temp = tempToken.nextToken();
temp = tempToken.nextToken();
tempToken = new StringTokenizer(temp, ")");
temp = tempToken.nextToken();
tempToken = new StringTokenizer(temp, ",");
for (int i = 0; i < 6; i++) {
numbers[i] = Integer.parseInt(tempToken.nextToken());
}

port = (numbers[4] * 256) + numbers[5];
ip = numbers[0] + "." + numbers[1] + "."
+ numbers[2] + "." + numbers[3];

InetAddress hostAddress = null;
try {
	hostAddress = InetAddress.getByName(ip);
} catch (UnknownHostException e) {
	// TODO 自動生成された catch ブロック
	e.printStackTrace();
}
byte[] address = hostAddress.getAddress();
Socket dataSocket = null;
try {
	dataSocket = new Socket(hostAddress, port);
} catch (IOException e1) {
	// TODO 自動生成された catch ブロック
	e1.printStackTrace();
}
try {
	dataSocket.setSoTimeout(30000);
} catch (SocketException e) {
	// TODO 自動生成された catch ブロック
	e.printStackTrace();
}
ctrlOutput.println("STOR " + storFileName);
ctrlOutput.flush();
try {
	line = ctrlInput.readLine();
} catch (IOException e) {
	// TODO 自動生成された catch ブロック
	e.printStackTrace();
}
while (line.charAt(3) == '-') {
System.out.println("STOR line=" + line);
try {
	line = ctrlInput.readLine();
} catch (IOException e) {
	// TODO 自動生成された catch ブロック
	e.printStackTrace();
}
}
System.out.println("STOR line=" + line);

int n;
int transferred = 0;
byte[] buff = new byte[1024];
DataOutputStream dataOutput = null;
try {
	dataOutput = new DataOutputStream(dataSocket.getOutputStream());
} catch (IOException e) {
	// TODO 自動生成された catch ブロック
	e.printStackTrace();
}
RandomAccessFile file = null;
try {
	file = new RandomAccessFile(fileName, "r");
} catch (FileNotFoundException e) {
	// TODO 自動生成された catch ブロック
	e.printStackTrace();
}
try {
	while ((n = file.read(buff)) > 0) {
	dataOutput.write(buff, 0, n);
	transferred += n;
	}
} catch (IOException e) {
	// TODO 自動生成された catch ブロック
	e.printStackTrace();
}

try {
	file.close();
} catch (IOException e) {
	// TODO 自動生成された catch ブロック
	e.printStackTrace();
}
try {
	dataOutput.close();
} catch (IOException e) {
	// TODO 自動生成された catch ブロック
	e.printStackTrace();
}
try {
	dataSocket.close();
} catch (IOException e) {
	// TODO 自動生成された catch ブロック
	e.printStackTrace();
}
} else {
System.out.println("ACTIVEモード");
FileInputStream fis = null;
try {
	fis = new FileInputStream(fileName);
} catch (FileNotFoundException e) {
	// TODO 自動生成された catch ブロック
	e.printStackTrace();
}
Socket dataSocket = null;
try {
	dataSocket = dataConnection("STOR " + storFileName);
} catch (UnknownHostException e) {
	// TODO 自動生成された catch ブロック
	e.printStackTrace();
} catch (IOException e) {
	// TODO 自動生成された catch ブロック
	e.printStackTrace();
}
OutputStream outstr = null;
try {
	outstr = dataSocket.getOutputStream();
} catch (IOException e1) {
	// TODO 自動生成された catch ブロック
	e1.printStackTrace();
}
int n;
byte[] buff = new byte[1024];
try {
	while ((n = fis.read(buff)) > 0) {
	outstr.write(buff, 0, n);
	}
} catch (IOException e) {
	// TODO 自動生成された catch ブロック
	e.printStackTrace();
}
try {
	dataSocket.close();
} catch (IOException e) {
	// TODO 自動生成された catch ブロック
	e.printStackTrace();
}
try {
	fis.close();
} catch (IOException e) {
	// TODO 自動生成された catch ブロック
	e.printStackTrace();
}
}

try {
	line = ctrlInput.readLine();
} catch (IOException e) {
	// TODO 自動生成された catch ブロック
	e.printStackTrace();
}
while (line.charAt(3) == '-') {
System.out.println("STOR line=" + line);
try {
	line = ctrlInput.readLine();
} catch (IOException e) {
	// TODO 自動生成された catch ブロック
	e.printStackTrace();
}
}
System.out.println("STOR line=" + line);

ctrlOutput.println("QUIT");
ctrlOutput.flush();
try {
	line = ctrlInput.readLine();
} catch (IOException e) {
	// TODO 自動生成された catch ブロック
	e.printStackTrace();
}
while (line.charAt(3) == '-') {
System.out.println("QUIT line=" + line);
try {
	line = ctrlInput.readLine();
} catch (IOException e) {
	// TODO 自動生成された catch ブロック
	e.printStackTrace();
}
}
System.out.println("QUIT line=" + line);

// 接続を閉じます
ctrlOutput.close();
try {
	ctrlInput.close();
} catch (IOException e) {
	// TODO 自動生成された catch ブロック
	e.printStackTrace();
}
try {
	ctrlSocket.close();
} catch (IOException e) {
	// TODO 自動生成された catch ブロック
	e.printStackTrace();
}
}

/**
* データ送受信用ソケットを取得
*
* @param ctrlcmd　
*/
private static Socket dataConnection(String ctrlcmd)
throws IOException, UnknownHostException {
String cmd = "PORT ";
ServerSocket serverDataSocket = new ServerSocket(0, 1);
for (int i = 0; i < 4; i++) {
cmd = cmd + (localHostAddress[i] & 0xff) + ",";
}
cmd += (((serverDataSocket.getLocalPort()) / 256) & 0xff)
+ "," + (serverDataSocket.getLocalPort() & 0xff);

ctrlOutput.println(cmd);
ctrlOutput.flush();
String line = ctrlInput.readLine();
while (line.charAt(3) == '-') {
System.out.println("PORT line=" + line);
line = ctrlInput.readLine();
}
System.out.println("PORT line=" + line);

ctrlOutput.println(ctrlcmd);
ctrlOutput.flush();
line = ctrlInput.readLine();
while (line.charAt(3) == '-') {
System.out.println("STOR line=" + line);
line = ctrlInput.readLine();
}

Socket dataSocket = serverDataSocket.accept();
serverDataSocket.close();
return dataSocket;
}
}
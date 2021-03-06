object AesEncryption{  
  def bytes2hex(bytes: Array[Byte]): String = {  
    val hex = new StringBuilder() //StringBuilder将字符串连接在一起 
    for (i<-0 to bytes.length - 1) {  
      val b = bytes(i)  
      var negative = false  
      if (b<0) {  
        negative = true  
      }  
      val inte = Math.abs(b)  
      val temp = Integer.toHexString(inte & 0xFF) //显示一个byte型的单字节十六进制(两位十六进制表示)的编码
      if (temp.length() == 1) {  
        hex.append("0")  
      }  
      // hex.append(temp.toLowerCase())  
      hex.append(temp)  
    }  
    hex.toString  
  }  
  @throws(classOf[Exception])  
  //生成密钥
  def genKeyAES(): String = {  
    val keyGen = KeyGenerator.getInstance("AES") // 返回生成指定算法的密钥
    keyGen.init(128)  
    val key = keyGen.generateKey()  
    val base64Str = Base64.getEncoder.encodeToString(key.getEncoded()) //实现Base64编码
    base64Str  
  }  
  //导入密钥
  def loadKeyAES(base64Key: String): SecretKey = {  
    val bytes = Base64.getDecoder.decode(base64Key)  
    val key = new SecretKeySpec(bytes,"AES")//创建密钥空间
    return key  
  } 
  //定义加密方法
  def encrytAES(file: String, key: SecretKey): Array[Byte] = {  
    val cipher = Cipher.getInstance("AES")  
    cipher.init(Cipher.ENCRYPT_MODE, key)//初始化Cipher对象
    val encryptBytes=Source.fromFile(file).mkString.getBytes  
    cipher.doFinal(encryptBytes)  
  }  
  def main(args:Array[String]):Unit= {   
    val keyAes = loadKeyAES(genKeyAes)  
    val cipher=bytes2hex(encrytAES(inputfile, keyAes))  
    new PrintWriter(new File(outputfile)).write(cipher)//在某些IDE中,直接采用print的方式速度慢,最好new一个输出
  }  
}  

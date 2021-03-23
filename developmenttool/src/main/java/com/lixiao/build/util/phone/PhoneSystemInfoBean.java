package com.lixiao.build.util.phone;

public class PhoneSystemInfoBean {
        //主板
        private String board;
        //系统定制商
        private String brand;
        //硬件制造商
        private String manufacturer;
        //显示屏参数
        private String displayInfo;
        //唯一编号
        private String fingderprint;
        //硬件序列号
        private String serial;
        //版本
        private String systemModel;
        //手机产品名
        private String productName;
        //安卓版本号
        private int vsdkint;
        //mac地址
        private String mac;
        //sv版本名称
        private String svBuildName;

        //SV版本号
        private String versionStr;
        //内存剩余空间，剩余的百分比
        private int readSystem;


        public String getBoard() {
            return board;
        }

        public void setBoard(String board) {
            this.board = board;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getManufacturer() {
            return manufacturer;
        }

        public void setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
        }

        public String getDisplayInfo() {
            return displayInfo;
        }

        public void setDisplayInfo(String displayInfo) {
            this.displayInfo = displayInfo;
        }

        public String getFingderprint() {
            return fingderprint;
        }

        public void setFingderprint(String fingderprint) {
            this.fingderprint = fingderprint;
        }

        public String getSerial() {
            return serial;
        }

        public void setSerial(String serial) {
            this.serial = serial;
        }

        public String getSystemModel() {
            return systemModel;
        }

        public void setSystemModel(String systemModel) {
            this.systemModel = systemModel;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public int getVsdkint() {
            return vsdkint;
        }

        public void setVsdkint(int vsdkint) {
            this.vsdkint = vsdkint;
        }

        public String getSvBuildName() {
            return svBuildName;
        }

        public void setSvBuildName(String svBuildName) {
            this.svBuildName = svBuildName;
        }

        public String getMac() {
            return mac;
        }

        public void setMac(String mac) {
            this.mac = mac;
        }


        public String getVersionStr() {
            return versionStr;
        }

        public void setVersionStr(String versionStr) {
            this.versionStr = versionStr;
        }

        public int getReadSystem() {
            return readSystem;
        }

        public void setReadSystem(int readSystem) {
            this.readSystem = readSystem;
        }

        @Override
        public String toString() {
            return "PhoneSystemInfoBean{" +
                    "board='" + board + '\'' +
                    ", brand='" + brand + '\'' +
                    ", manufacturer='" + manufacturer + '\'' +
                    ", displayInfo='" + displayInfo + '\'' +
                    ", fingderprint='" + fingderprint + '\'' +
                    ", serial='" + serial + '\'' +
                    ", systemModel='" + systemModel + '\'' +
                    ", productName='" + productName + '\'' +
                    ", vsdkint=" + vsdkint +
                    ", svBuildName='" + svBuildName + '\'' +
                    ", mac='" + mac + '\'' +
                    ", versionStr='" + versionStr + '\'' +
                    ", readSystem=" + readSystem +
                    '}';
        }


    }
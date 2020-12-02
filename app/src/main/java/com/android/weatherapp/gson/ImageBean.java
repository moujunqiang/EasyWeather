package com.android.weatherapp.gson;


import java.util.List;

public class ImageBean {

    /**
     * images : [{"startdate":"20201201","fullstartdate":"202012011600","enddate":"20201202","url":"/th?id=OHR.PorcupineBay_ZH-CN2252758146_1920x1080.jpg&rf=LaDigue_1920x1080.jpg&pid=hp","urlbase":"/th?id=OHR.PorcupineBay_ZH-CN2252758146","copyright":"基奈峡湾国家公园的尖顶湾，阿拉斯加 (© Sekar B/Shutterstock)","copyrightlink":"https://www.bing.com/search?q=%E5%9F%BA%E5%A5%88%E5%B3%A1%E6%B9%BE%E5%9B%BD%E5%AE%B6%E5%85%AC%E5%9B%AD&form=hpcapt&mkt=zh-cn","title":"","quiz":"/search?q=Bing+homepage+quiz&filters=WQOskey:%22HPQuiz_20201201_PorcupineBay%22&FORM=HPQUIZ","wp":true,"hsh":"4b729291a1259dbc3355d68efe6de423","drk":1,"top":1,"bot":1,"hs":[]}]
     * tooltips : {"loading":"正在加载...","previous":"上一个图像","next":"下一个图像","walle":"此图片不能下载用作壁纸。","walls":"下载今日美图。仅限用作桌面壁纸。"}
     */

    private TooltipsBean tooltips;
    private List<ImagesBean> images;

    public TooltipsBean getTooltips() {
        return tooltips;
    }

    public void setTooltips(TooltipsBean tooltips) {
        this.tooltips = tooltips;
    }

    public List<ImagesBean> getImages() {
        return images;
    }

    public void setImages(List<ImagesBean> images) {
        this.images = images;
    }

    public static class TooltipsBean {
        /**
         * loading : 正在加载...
         * previous : 上一个图像
         * next : 下一个图像
         * walle : 此图片不能下载用作壁纸。
         * walls : 下载今日美图。仅限用作桌面壁纸。
         */

        private String loading;
        private String previous;
        private String next;
        private String walle;
        private String walls;

        public String getLoading() {
            return loading;
        }

        public void setLoading(String loading) {
            this.loading = loading;
        }

        public String getPrevious() {
            return previous;
        }

        public void setPrevious(String previous) {
            this.previous = previous;
        }

        public String getNext() {
            return next;
        }

        public void setNext(String next) {
            this.next = next;
        }

        public String getWalle() {
            return walle;
        }

        public void setWalle(String walle) {
            this.walle = walle;
        }

        public String getWalls() {
            return walls;
        }

        public void setWalls(String walls) {
            this.walls = walls;
        }
    }

    public static class ImagesBean {
        /**
         * startdate : 20201201
         * fullstartdate : 202012011600
         * enddate : 20201202
         * url : /th?id=OHR.PorcupineBay_ZH-CN2252758146_1920x1080.jpg&rf=LaDigue_1920x1080.jpg&pid=hp
         * urlbase : /th?id=OHR.PorcupineBay_ZH-CN2252758146
         * copyright : 基奈峡湾国家公园的尖顶湾，阿拉斯加 (© Sekar B/Shutterstock)
         * copyrightlink : https://www.bing.com/search?q=%E5%9F%BA%E5%A5%88%E5%B3%A1%E6%B9%BE%E5%9B%BD%E5%AE%B6%E5%85%AC%E5%9B%AD&form=hpcapt&mkt=zh-cn
         * title :
         * quiz : /search?q=Bing+homepage+quiz&filters=WQOskey:%22HPQuiz_20201201_PorcupineBay%22&FORM=HPQUIZ
         * wp : true
         * hsh : 4b729291a1259dbc3355d68efe6de423
         * drk : 1
         * top : 1
         * bot : 1
         * hs : []
         */

        private String startdate;
        private String fullstartdate;
        private String enddate;
        private String url;
        private String urlbase;
        private String copyright;
        private String copyrightlink;
        private String title;
        private String quiz;
        private boolean wp;
        private String hsh;
        private int drk;
        private int top;
        private int bot;
        private List<?> hs;

        public String getStartdate() {
            return startdate;
        }

        public void setStartdate(String startdate) {
            this.startdate = startdate;
        }

        public String getFullstartdate() {
            return fullstartdate;
        }

        public void setFullstartdate(String fullstartdate) {
            this.fullstartdate = fullstartdate;
        }

        public String getEnddate() {
            return enddate;
        }

        public void setEnddate(String enddate) {
            this.enddate = enddate;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrlbase() {
            return urlbase;
        }

        public void setUrlbase(String urlbase) {
            this.urlbase = urlbase;
        }

        public String getCopyright() {
            return copyright;
        }

        public void setCopyright(String copyright) {
            this.copyright = copyright;
        }

        public String getCopyrightlink() {
            return copyrightlink;
        }

        public void setCopyrightlink(String copyrightlink) {
            this.copyrightlink = copyrightlink;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getQuiz() {
            return quiz;
        }

        public void setQuiz(String quiz) {
            this.quiz = quiz;
        }

        public boolean isWp() {
            return wp;
        }

        public void setWp(boolean wp) {
            this.wp = wp;
        }

        public String getHsh() {
            return hsh;
        }

        public void setHsh(String hsh) {
            this.hsh = hsh;
        }

        public int getDrk() {
            return drk;
        }

        public void setDrk(int drk) {
            this.drk = drk;
        }

        public int getTop() {
            return top;
        }

        public void setTop(int top) {
            this.top = top;
        }

        public int getBot() {
            return bot;
        }

        public void setBot(int bot) {
            this.bot = bot;
        }

        public List<?> getHs() {
            return hs;
        }

        public void setHs(List<?> hs) {
            this.hs = hs;
        }
    }
}

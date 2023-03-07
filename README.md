# 專案名稱fei_mall
此為簡易的商品購物平台後台，使用SpringMVC進行專案設計

## 專案介紹
此專案簡易的電商系統，包含前台商城系統及後台管理系統，基於SpringBoot與MyBatis實現。
- 前台商城系統
  - 首頁
  - 商品推薦(熱門商品、優惠商品、新上架商品)
  - 商品搜尋
  - 商品詳情
  - 購物車
  - 訂單流程
  - 會員中心
- 後台管理系統
  - 訂單列表
  - 商品管理
  - 品牌管理
  
## 組織結構
 <pre><code>
mall
├── mall-Brand -- 品牌相關功能
├── mall-Cart -- 購物車相關功能
├── mall-Keyword -- 搜尋功能關鍵字管理功能
├── mall-Order -- 訂單相關功能
├── mall-Password -- 密碼管理功能
├── mall-Payment -- paypal金流管理系統
├── mall-Product -- 商品管理功能
├── mall-Search -- 基於Elasticsearch的商品搜尋系統
└── mall-User -- 用戶管理相關功能
 </code></pre>
 
## 使用到的技術
| 技術 | 說明 |
| --- | --- | 
| SpringBoot | Web應用開發 | 
| SpringSecurity | 認證與授權 | 
| MyBatis	 | ORM框架 | 
| Elasticsearch | 搜索引擎 | 
| Spring Validation | 資料驗證 | 
| PageHelper | MyBatis分頁外掛 | 
| Redis | 記憶體資料存放區 | 
| Quartz | 任務排程 | 

## 系統架構
![Image text](https://github.com/Roshiahsu/img-folder/blob/main/1.png)

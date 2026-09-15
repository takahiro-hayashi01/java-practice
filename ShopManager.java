// 【Java学習まとめプログラム】ショップの在庫・購入管理
// Notion「Java 学習ページ」1〜10章の内容を1本のプログラムに詰め込んでいます。
// 9章(パッケージ)のみ、1ファイル構成のため実装せず、コメントで仕組みを説明しています。

import java.util.ArrayList;
import java.math.BigDecimal;

public class ShopManager {

    // ---- 7章：クラスの基礎（static, private, カプセル化） ----
    private static int productCount = 0; // static：全インスタンス共通のカウンタ

    static class Product {
        private String name;   // private：外部から直接書き換えさせない
        private int price;
        private int stock;

        // コンストラクタ：newした瞬間に1回だけ実行される
        public Product(String name, int price, int stock) {
            this.name = name;
            this.price = price;
            this.stock = stock;
            productCount++;
        }

        // publicなメソッド経由でのみ値を取得させる（getter）
        public String getName() { return name; }
        public int getPrice() { return price; }
        public int getStock() { return stock; }

        public void reduceStock(int qty) { // インスタンスメソッド
            this.stock -= qty;
        }

        @Override
        public String toString() { // printlnで自動的に使われる
            return name + "(在庫:" + stock + ")";
        }
    }

    public static void main(String[] args) {

        // ---- 3章：変数・型・定数・演算子 ----
        final double TAX_RATE = 0.10;          // 定数（マジックナンバーを避ける）
        int quantity = 3;
        double unitPrice = 150.0;
        double subtotal = quantity * unitPrice; // 演算子

        // ---- 4章：配列・拡張for文・String ----
        String[] categories = {"飲料", "食品", "日用品"};
        for (String category : categories) {
            System.out.println("カテゴリ: " + category);
        }

        // ---- 6章：switch文（新しい書き方） ----
        String rank = "gold";
        double discountRate;
        switch (rank) {
            case "platinum" -> discountRate = 0.20;
            case "gold"     -> discountRate = 0.10;
            default         -> discountRate = 0.0;
        }

        // ---- 8章：ArrayList（可変長の配列） ----
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("コーラ", 150, 10));
        products.add(new Product("お茶", 130, 5));
        products.add(new Product("水", 100, 0));

        // ---- 5章：for文（合計計算） ----
        int totalStock = 0;
        for (int i = 0; i < products.size(); i++) {
            totalStock += products.get(i).getStock();
        }
        System.out.println("総在庫数: " + totalStock);

        // ---- 5章：while文 / 6章：if文 ----
        int index = 0;
        while (index < products.size()) {
            if (products.get(index).getStock() == 0) {
                System.out.println(products.get(index).getName() + "は在庫切れです");
            }
            index++;
        }

        // ---- 8章：BigDecimal（誤差のない金額計算） ----
        BigDecimal bdPrice = new BigDecimal("150");
        BigDecimal bdQty = new BigDecimal("3");
        System.out.println("BigDecimal合計: " + bdPrice.multiply(bdQty));

        // ---- 8章：例外処理（try-catch-finally） ----
        // 実行例: java ShopManager 0
        try {
            int selected = Integer.parseInt(args.length > 0 ? args[0] : "0"); // 文字列→数値
            if (selected < 0 || selected >= products.size()) {
                throw new IllegalArgumentException("存在しない商品番号です");
            }
            Product target = products.get(selected);
            if (target.getStock() <= 0) {
                System.out.println(target.getName() + "は品切れのため購入できません");
            } else {
                target.reduceStock(1);
                System.out.println(target.getName() + "を1個購入しました。残り在庫: " + target.getStock());
            }
        } catch (NumberFormatException e) {
            System.out.println("数値変換に失敗しました: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("入力エラー: " + e.getMessage());
        } finally {
            System.out.println("処理を終了します(登録商品数: " + productCount + ")");
        }

        // ---- 6章：三項演算子 / 3章：キャスト ----
        double taxedTotal = subtotal * (1 - discountRate) * (1 + TAX_RATE);
        String shipping = (taxedTotal >= 1000) ? "送料無料" : "送料300円";
        System.out.println("税込合計: " + (int) taxedTotal + "円(" + shipping + ")"); // (int)キャスト＝切り捨て

        // ---- 5章：do-while文 ----
        int retry = 0;
        do {
            System.out.println("在庫チェック" + retry + "回目");
            retry++;
        } while (retry < 2);

        // ---- 8章：Math（丸め） ----
        System.out.println("四捨五入: " + Math.round(taxedTotal));
    }
}

/*
 * 【9章 パッケージについて】
 * 本来はクラスが増えると、下記のようにファイルを分けて整理します。
 *   package com.example.shop;      → com/example/shop/ フォルダに配置
 *   import com.example.shop.Product; → 別パッケージのクラスを読み込む
 * 1ファイルにまとめた今回は省略していますが、考え方はこのコメントの通りです。
 */

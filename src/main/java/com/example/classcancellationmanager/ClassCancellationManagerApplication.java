package com.example.classcancellationmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Bootアプリケーションのエントリーポイント（起動クラス）です。
 * `@SpringBootApplication`アノテーションにより、自動設定、コンポーネントスキャンなどが行われます。
 */
@SpringBootApplication
public class ClassCancellationManagerApplication {

	/**
	 * アプリケーションを起動するmainメソッドです。
	 * @param args 起動時のコマンドライン引数
	 */
	public static void main(String[] args) {
		SpringApplication.run(ClassCancellationManagerApplication.class, args);
	}

}

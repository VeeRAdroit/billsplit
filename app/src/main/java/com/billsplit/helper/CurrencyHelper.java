/**
 * 
 */
package com.billsplit.helper;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.Validate;

import com.billsplit.constant.Currency;

/**
 * @author veeraj
 *
 */
public class CurrencyHelper {

	static Map<String, Float> exchangeRateMap;
	static {
		exchangeRateMap = new HashMap<String, Float>();

		exchangeRateMap.put("USD:INR", new Float(60f));
		exchangeRateMap.put("GBP:INR", new Float(80f));
		exchangeRateMap.put("EUR:INR", new Float(70f));
		exchangeRateMap.put("INR:USD", new Float(1/60f));
		exchangeRateMap.put("INR:GBP", new Float(1/80f));
		exchangeRateMap.put("INR:EUR", new Float(1/70f));
	}

	public static Float convertCurrencyValue(Float value,
			Currency fromCurrency, Currency toCurrency) {

		Validate.notNull(value);
		Float convertedValue = value
				* fetchExchangeRate(fromCurrency, toCurrency);
	/*	System.out
				.println(String
						.format("FromCurrency:%s, ToCurrency:%s, Value:%.2f, ConvertedValue:%.2f",
								fromCurrency, toCurrency, value, convertedValue));
	*/	return convertedValue;
	}

	public static Float fetchExchangeRate(Currency fromCurrency,
			Currency toCurrency) {
		Float exchangeRate = new Float(1);
		String exchangeRateKey = "";
		if (!fromCurrency.equals(toCurrency)) {
			exchangeRateKey = String.format("%s:%s", fromCurrency, toCurrency);
			exchangeRate = exchangeRateMap.get(exchangeRateKey);
	/*		System.out.println(String.format(
					"Fetched ExchangeRate %.2f for Key %s", exchangeRate,
					exchangeRateKey));
	*/	}
		return exchangeRate;
	}

}

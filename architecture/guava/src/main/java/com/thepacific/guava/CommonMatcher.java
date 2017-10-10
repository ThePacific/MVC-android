package com.thepacific.guava;

abstract class CommonMatcher {

  abstract boolean matches();

  abstract boolean find();

  abstract boolean find(int index);

  abstract String replaceAll(String replacement);

  abstract int end();

  abstract int start();
}

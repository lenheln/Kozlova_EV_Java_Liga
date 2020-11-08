package com.example.social_network.utils;

import org.w3c.dom.CharacterData;

import java.util.HashMap;

public class KeyboardConverter {

    /**
     * Конвертирует строку в строку в другой раскладке клавиатуры
     *
     * @param string
     * @return
     */

    public static String convert(String string){
        HashMap<Character, Character> keyboard = getKeyboard();

        char[] chars = string.toCharArray();
        char[] result = new char[chars.length];
        for (int i = 0; i < chars.length; i++) {
            Character letter = Character.toLowerCase(chars[i]);
            if(keyboard.containsKey(letter)) {
                result[i] = keyboard.get(letter);
            }
            else{
                return null;
            }
        }
        return new String(result);
    }

    public static HashMap<Character, Character> getKeyboard(){
        HashMap<Character, Character> keyboard = new HashMap();
        keyboard.put('q','й');
        keyboard.put('w','ц');
        keyboard.put('e','у');
        keyboard.put('r','к');
        keyboard.put('t','е');
        keyboard.put('y','н');
        keyboard.put('u','г');
        keyboard.put('i','ш');
        keyboard.put('o','щ');
        keyboard.put('p','з');
        keyboard.put('[','х');
        keyboard.put(']','ъ');
        keyboard.put('a','ф');
        keyboard.put('s','ы');
        keyboard.put('d','в');
        keyboard.put('f','а');
        keyboard.put('g','п');
        keyboard.put('h','р');
        keyboard.put('j','о');
        keyboard.put('k','л');
        keyboard.put('l','д');
        keyboard.put(';','ж');
        keyboard.put('\'', 'э');
        keyboard.put('z','я');
        keyboard.put('x','ч');
        keyboard.put('c','с');
        keyboard.put('v','м');
        keyboard.put('b','и');
        keyboard.put('n','т');
        keyboard.put('m','ь');
        keyboard.put(',','б');
        keyboard.put('.','ю');
        keyboard.put('{','х');
        keyboard.put('}','ъ');
        keyboard.put(':','ж');
        keyboard.put('"','э');
        keyboard.put('<','б');
        keyboard.put('>','ю');
        return keyboard;
    }
}
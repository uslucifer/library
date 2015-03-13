/*
 * библиотекарь
 */
package library;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Нежнов Алексей usLucifer
 */
public class Library {
    private static int serial_num;
   Hashtable <String,Hashtable<String,String>> main_library;
    Hashtable <String, String> reaction;
    String filename = "data\\main_lib.dat";
    String filename_1 = "data\\main_reaction.dat";
    String echo; // эхо, последнее множество на входе
    boolean reaction_flag;
    private String session;
    boolean last_multiude; 
    public Library() {
       
        serial_num = 0;
        main_library = new Hashtable <String,Hashtable<String, String>>();
        reaction = new Hashtable <String, String>();
        reaction_flag = true;
        last_multiude = false;
        session="";
        echo ="";
    }

    void addMultitude(String multitude)
    {// добавляет библиотеку новым множеством
        
        String key = multitude.length()+"";// формируем ключ
        echo = multitude;//  формируем эхо множество
        if(main_library.containsKey(key))
        {
            Hashtable<String,String> mul = main_library.get(key);
            if(!mul.containsKey(multitude))// если множество неизвестно
            {
                serial_num++;
                mul.put(multitude,serial_num+"");
                main_library.put(key, mul);
                reaction_flag = false; // реакция на неизвестное множество - интерес
            }else{// если множество найдено
                reaction_flag = true; // реакция на неизвестное множество - отсутствие интереса
            }
        }
        else
        {//создаем новый ключ
            Hashtable<String,String> mul = new  Hashtable<String,String>();
            serial_num++;
            mul.put(multitude,serial_num+"");
            main_library.put(key, mul);
        }   
        
    }
    void saveLibary()
    {   try {
    
        FileOutputStream file_output = new FileOutputStream(filename);
        FileOutputStream file_output_1 = new FileOutputStream(filename_1);
                ObjectOutputStream output_object_1 = new ObjectOutputStream(file_output_1);
                ObjectOutputStream output_object = new ObjectOutputStream(file_output);
                output_object.writeObject(main_library);
                output_object_1.writeObject(reaction);
               
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Library.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Library.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    void loadLibrary()
    {   try {
        // загружает состояние библиотеки из файла
        FileInputStream input_stream = new FileInputStream(filename);
        FileInputStream input_stream_1 = new FileInputStream(filename_1);
                ObjectInputStream input_object = new ObjectInputStream(input_stream);
                ObjectInputStream input_object_1 = new ObjectInputStream(input_stream_1);
                main_library =  (Hashtable<String, Hashtable<String, String>>) input_object.readObject();
                reaction = (Hashtable<String, String>) input_object_1.readObject();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Library.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Library.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Library.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    // вспомогательные методы
    int getSize()
    {
        int size =0;
        for(Enumeration<String> tt = main_library.keys();tt.hasMoreElements(); )
            size = size + main_library.get(tt.nextElement()).size();
    return size;
    }

    /**
     * @return the session
     */
    public String getSession() {
        return session;
    }
    boolean getReaction_flag()
    {
        return reaction_flag;
    }
    String getEcho()
    {
        return echo;
    }
    
}

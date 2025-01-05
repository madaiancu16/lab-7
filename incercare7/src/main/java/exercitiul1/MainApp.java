package exercitiul1;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class MainApp {
    public static Map<Integer,Carte> citire()
    {
        try
        {
            ObjectMapper mapper=new ObjectMapper();
            Map<Integer,Carte>carti_map=mapper.readValue(new File("src/main/resources/carti.json"),new TypeReference<Map<Integer,Carte>>(){});
            return carti_map;

        } catch(StreamReadException e)
        {
            throw new RuntimeException(e);
        } catch(DatabindException e)
        {
            throw new RuntimeException(e);
        } catch(IOException e)
        {
            throw new RuntimeException(e);
        }


    }
    public static void scriere(Map<Integer,Carte> carti)
    {
        try
        {
            ObjectMapper mapper=new ObjectMapper();
            File fis=new File("src/main/resources/carti.json");
            mapper.writeValue(fis,carti);

        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }
    public static void afisare(Map<Integer,Carte>carti_map)
    {
        var entryset=carti_map.entrySet();
        var it=entryset.iterator();
        while(it.hasNext())
        {
            var m=it.next();
            System.out.println(m.getKey()+" : "+m.getValue());
        }
        System.out.println();
    }
    public static void stergere(Map<Integer,Carte>carti_map,int id)
    {
        var entryset=carti_map.entrySet();
        var it=entryset.iterator();
        while(it.hasNext())
        {
            var m=it.next();

            if(m.getKey()==id)
            {
               it.remove();
            }

        }
    }
    public static void main(String[] args)
    {
        Map<Integer,Carte> carti_map=new HashMap<Integer,Carte>();
        carti_map=citire();
        Scanner scanner = new Scanner(System.in);
        int opt;
        do {

            System.out.println("\n--- Meniu ---");
            System.out.println("1. Afișarea cărților");
            System.out.println("2. Adăugarea unei cărți");
            System.out.println("3. Ștergerea unei înregistrări");
            System.out.println("4. Salvarea modificărilor");
            System.out.println("5. Crearea unui Set<Carte>");
            System.out.println("6. Afișarea ordonată după titlu din colecția Set");
            System.out.println("7. Afișarea celor mai vechi cărți");
            System.out.println("0. Ieșire");
            System.out.print("Alegeți o opțiune: ");
            opt = scanner.nextInt();
            scanner.nextLine();
            switch (opt)
            {
                case 0:
                    break;
                    case 1:
                        afisare(carti_map);
                        break;
                case 2:
                    Scanner scanner3= new Scanner(System.in);
                  boolean  ks=false;
                  do {
                      try
                      {
                          int id;
                          System.out.println("introdu id-ul ");
                          id=scanner3.nextInt();
                          scanner3.nextLine();
                          stergere(carti_map,id);
                          ks=true;

                      }catch(InputMismatchException e)
                      {
                          System.out.println("Invalid! ");
                          scanner3.nextLine();
                      }

                  }while(!ks);
                    afisare(carti_map);
                  break;

                case 3:
                    Scanner scanner2=new Scanner(System.in);
                    boolean k=false;
                    do {
                        try
                        { int id;
                            String titlul;
                            String autorul;
                            int anul;
                            System.out.println("id: ");
                            id = scanner2.nextInt();
                            scanner2.nextLine();
                            System.out.println("titlul: ");
                            titlul = scanner2.nextLine();
                            System.out.println("autorul: ");
                            autorul = scanner2.nextLine();
                            System.out.println("anul: ");
                            anul = scanner2.nextInt();
                            carti_map.putIfAbsent(id,new Carte(titlul,autorul,anul));
                            k=true;

                        }catch (InputMismatchException e)
                        {
                            System.out.println("Invalid ");
                            scanner2.nextLine();
                        }
                    }while(!k);
                    break;
                case 4:
                    scriere(carti_map);
                    System.out.println("modificarile au avut loc");
                    break;
                    case 5:
                        Set<Carte>carti_set=carti_map.values()
                                .stream()
                                .filter((a)->a.autorul().equalsIgnoreCase("Yuval Noah Harari"))
                                .collect(Collectors.toSet());
                        carti_set.forEach(System.out::println);
                        break;
                        case 6:
                           carti_map.values()
                                    .stream()
                                    .sorted((a,b)->a.titlul().compareToIgnoreCase(b.titlul()))
                                    .forEach(System.out::println);
                            break;
                case 7:
                    Optional<Carte>carte_veche= carti_map.values()
                            .stream()
                            .min(Comparator.comparing(Carte::an));
                    if(carte_veche.isPresent())
                    {
                        carti_map.values()
                                .stream()
                                .filter((a)->a.an()==carte_veche.get().an())
                                .forEach(System.out::println);
                    }
              else
                    {
                        System.out.println("imposibil de gasit cartea! ");
                    }

            }
        }while(opt!=0);

    }

}

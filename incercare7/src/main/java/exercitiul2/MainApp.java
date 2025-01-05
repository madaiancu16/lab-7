package exercitiul2;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.*;

public class MainApp {
    public static void scriere(Set<InstrumentMuzical>set_instrumente)
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            File file=new File("src/main/resources/instrumente_muzicale.json");
            mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator());
            mapper.writeValue(file,set_instrumente);
        }catch(Exception e)
        {
                e.printStackTrace();
        }

    }
        public static Set<InstrumentMuzical> citire()
        {
            try
            {
                ObjectMapper mapper = new ObjectMapper();
                File file=new File("src/main/resources/instrumente_muzicale.json");
                mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator());
                return mapper.readValue(file,new TypeReference<HashSet<InstrumentMuzical>>(){});
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        public static void verificare_duplicate(Set<InstrumentMuzical>set_instrumente)
        {
            var it=set_instrumente.iterator();
            InstrumentMuzical im=it.next();
            Set<InstrumentMuzical>set_instrumente2=new HashSet<>(set_instrumente);
            set_instrumente.add(im);
            if(set_instrumente2.size()==set_instrumente.size())
            {
                System.out.println("NU SE PERMITE DUPLICATE");
            }
            else
            {
                System.out.println("SE PERMITE DUPLICATE");
            }

        }
        public static void afisare_set(Set<InstrumentMuzical>set_instrumente)
        {
            Optional<Set<InstrumentMuzical>>opt=Optional.empty();
            opt=Optional.ofNullable(set_instrumente);
            opt.ifPresentOrElse(System.out::println,()->{
                System.out.println("lipseste ");
            });

        }
        public static void main(String[] args)
        {
            Set<InstrumentMuzical>set_instrumente=new HashSet<InstrumentMuzical>();
            set_instrumente.add(new Chitara("Gibson", 4500, TipChitara.valueOf("ELECTRICA"), 6));
            set_instrumente.add(new Chitara("Taylor", 2800, TipChitara.valueOf("ACUSTICA"), 12));
            set_instrumente.add(new Chitara("Cort", 1800, TipChitara.valueOf("CLASICA"), 6));

            set_instrumente.add(new SetTobe("Roland", 5200, TipTobe.valueOf("ELECTRONICE"), 5, 3));
            set_instrumente.add(new SetTobe("Pearl", 4300, TipTobe.valueOf("ACUSTICE"), 7, 2));
            set_instrumente.add(new SetTobe("Mapex", 2500, TipTobe.valueOf("ACUSTICE"), 4, 1));
            Scanner scanner=new Scanner(System.in);
            Set<InstrumentMuzical>set_instrumente_file=new HashSet<InstrumentMuzical>() {};
            int opt;
            do {
                System.out.println("\n--- Meniu ---");
                System.out.println("1. Am creat colectia  si pt chitari si pt tobe: ");
                System.out.println("2. salvarea colectiei in fisier");
                System.out.println("3. incarcarea datelor facuta! ");
                System.out.println("4. afisarea ");
                System.out.println("5. verificarea duplicatelor ");
                System.out.println("6. stergerea instrumentelor din set al caror pret>3000");
                System.out.println("7. afisarea datelor chitarilor");
                System.out.println("8. Afișarea datelor tobelor ");
                System.out.println("9. afisarea datelor chitarii care are cele mai multe corzi ");
                System.out.println("10. afisarea datelor tobelor acustice ,ord dupa nr. de tobe ");
                System.out.println("0. Ieșire");
                System.out.print("Alegeți o opțiune: ");
                opt=scanner.nextInt();
                scanner.nextLine();

                switch (opt)
                {
                    case 0:
                        break;
                    case 1:
                        System.out.println("am facut colectia mai sus ");
                        break;
                        case 2:
                            scriere(set_instrumente);
                            System.out.println("Modificarile au avut loc ");
                            break;
                    case 4:
                        afisare_set(set_instrumente_file);
                        set_instrumente_file=citire();
                        set_instrumente_file.forEach(System.out::println);
                     break;
                    case 5:
                        verificare_duplicate(set_instrumente_file);
                        break;
                        case 6:
                          set_instrumente_file.removeIf((a)->a.getPret()>3000);
                          set_instrumente_file.forEach(System.out::println);
                          break;
                          case 7:
                              set_instrumente_file
                                      .stream()
                                      .filter((a)->a instanceof Chitara)
                                      .forEach(System.out::println);
                              break;
                    case 8:
                        set_instrumente_file
                                .stream()
                                .filter((a)->a.getClass()== SetTobe.class)
                                .forEach(System.out::println);
                        break;
                        case 9:
                            Optional<InstrumentMuzical>opt_chitara_nrcorzi=
                                    set_instrumente_file
                                            .stream()
                                            .filter((a)->a instanceof Chitara)
                                            .max(Comparator.comparing((a)->((Chitara)a). getNr_corzi()));
opt_chitara_nrcorzi.ifPresent(System.out::println);
break;
case 10:
    set_instrumente_file
            .stream()
            .filter((a)->a instanceof SetTobe )
            .sorted((a,b)->((SetTobe)a).getNr_tobe()-((SetTobe)b).getNr_tobe())
            .forEach(System.out::println);
      break;



                }

            }while(opt!=0);
        }
}

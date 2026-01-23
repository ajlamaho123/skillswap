package com.skillswap.skillswap.konfigurime;

import com.skillswap.skillswap.modelet.*;
import com.skillswap.skillswap.modelet.enums.RoliPerdoruesit;
import com.skillswap.skillswap.modelet.enums.StatusEventi;
import com.skillswap.skillswap.modelet.enums.StatusRegjistrimi;
import com.skillswap.skillswap.ruajtesat.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@SuppressWarnings("unused")
@Component
public class DataSeeder implements CommandLineRunner {

    private final PerdoruesiRuajtesa perdoruesiRuajtesa;
    private final KategoriAftesieRuajtesa kategoriRuajtesa;
    private final EventiRuajtesa eventiRuajtesa;
    private final RegjistrimiRuajtesa regjistrimiRuajtesa;
    private final VleresimiRuajtesa vleresimiRuajtesa;

    public DataSeeder(PerdoruesiRuajtesa perdoruesiRuajtesa,
                      KategoriAftesieRuajtesa kategoriRuajtesa,
                      EventiRuajtesa eventiRuajtesa,
                      RegjistrimiRuajtesa regjistrimiRuajtesa,
                      VleresimiRuajtesa vleresimiRuajtesa) {
        this.perdoruesiRuajtesa = perdoruesiRuajtesa;
        this.kategoriRuajtesa = kategoriRuajtesa;
        this.eventiRuajtesa = eventiRuajtesa;
        this.regjistrimiRuajtesa = regjistrimiRuajtesa;
        this.vleresimiRuajtesa = vleresimiRuajtesa;
    }

    @SuppressWarnings("null")
    @Override
    public void run(String... args) {

        // Nese ke evente (ose kategori), supozojme qe seed eshte bere me pare -> mos e perserit
        if (kategoriRuajtesa.count() > 0 || eventiRuajtesa.count() > 0) {
            return;
        }

        // 1) KATEGORI
        KategoriAftesie programim = kategoriRuajtesa.save(new KategoriAftesie("Programim"));
        KategoriAftesie gatim = kategoriRuajtesa.save(new KategoriAftesie("Gatim"));
        KategoriAftesie fotografi = kategoriRuajtesa.save(new KategoriAftesie("Fotografi"));
        KategoriAftesie fitness = kategoriRuajtesa.save(new KategoriAftesie("Fitness"));
        KategoriAftesie gjuhe = kategoriRuajtesa.save(new KategoriAftesie("Gjuhe"));

        // 2) USERA
        // Password i thjeshte per demo (pa BCrypt) - siç e ke bere ne Fazen 2
        Perdoruesi host = perdoruesiRuajtesa.save(
                new Perdoruesi("Host", "One", "host1@gmail.com", "12345", RoliPerdoruesit.PERDORUES)
        );

        Perdoruesi user2 = perdoruesiRuajtesa.save(
                new Perdoruesi("Ana", "Kola", "ana@gmail.com", "12345", RoliPerdoruesit.PERDORUES)
        );

        Perdoruesi user3 = perdoruesiRuajtesa.save(
                new Perdoruesi("Ena", "Doda", "ena@gmail.com", "12345", RoliPerdoruesit.PERDORUES)
        );

        // 3) EVENTE (gjithmone ne te ardhmen)
        LocalDate d1 = LocalDate.now().plusDays(3);
        LocalDate d2 = LocalDate.now().plusDays(5);
        LocalDate d3 = LocalDate.now().plusDays(7);

        Eventi eLimit1 = krijoEvent(
                "Learn Java in 2 hours – beginner friendly",
                "Workshop per bazat e Java-s. Variabla, kushte, cikle, dhe OOP bazike.",
                d1, LocalTime.of(17, 0),
                "Shkoder - Qender",
                1, // LIMIT 1 per test
                host,
                programim
        );

        Eventi eNormal = krijoEvent(
                "Photography walk in the city",
                "Ecje ne qytet dhe praktike fotografie me detyra te thjeshta.",
                d2, LocalTime.of(10, 0),
                "Shkoder",
                15,
                host,
                fotografi
        );

        Eventi eGjuhe = krijoEvent(
                "Arabic conversation café",
                "Praktikim bisede ne arabisht per fillestare. Tema te perditshme.",
                d3, LocalTime.of(18, 30),
                "Kafe Qyteti",
                20,
                host,
                gjuhe
        );

        eLimit1 = eventiRuajtesa.save(eLimit1);
        eNormal = eventiRuajtesa.save(eNormal);
        eGjuhe = eventiRuajtesa.save(eGjuhe);

        // 4) REGJISTRIME
        // Regjistro user2 te eventi me limit=1 (pastaj user3 duhet te deshtoje kur e provon me Postman)
        Regjistrimi r1 = new Regjistrimi();
        r1.setEventi(eLimit1);
        r1.setPerdoruesi(user2);
        r1.setDataRegjistrimit(LocalDateTime.now());
        r1.setStatusi(StatusRegjistrimi.AKTIV);
        regjistrimiRuajtesa.save(r1);

        // Regjistro user3 te nje event tjeter normal
        Regjistrimi r2 = new Regjistrimi();
        r2.setEventi(eNormal);
        r2.setPerdoruesi(user3);
        r2.setDataRegjistrimit(LocalDateTime.now());
        r2.setStatusi(StatusRegjistrimi.AKTIV);
        regjistrimiRuajtesa.save(r2);

        // 5) VLEReSIME (rating)
        // User3 ka regjistrim te eNormal, prandaj mund ta vleresoje host-in per ate event
        Vleresimi v1 = new Vleresimi();
        v1.setEventi(eNormal);
        v1.setVleresuesi(user3);
        v1.setHosti(host);
        v1.setYje(5);
        v1.setKoment("Shume e qarte dhe shume miqesore!");
        v1.setDataKrijimit(LocalDateTime.now());
        vleresimiRuajtesa.save(v1);

        // User2 ka regjistrim te eLimit1, mund te vleresoje per ate event
        Vleresimi v2 = new Vleresimi();
        v2.setEventi(eLimit1);
        v2.setVleresuesi(user2);
        v2.setHosti(host);
        v2.setYje(4);
        v2.setKoment("Shume mire, do doja edhe me shume ushtrime.");
        v2.setDataKrijimit(LocalDateTime.now());
        vleresimiRuajtesa.save(v2);

        System.out.println("✅ DataSeeder: u futen kategori, usera, evente, regjistrime dhe vleresime.");
        System.out.println("👉 Login demo:");
        System.out.println("Host: host1@gmail.com / 12345");
        System.out.println("User2: ana@gmail.com / 12345");
        System.out.println("User3: ena@gmail.com / 12345");
        System.out.println("👉 Test limit: provo te regjistrosh user3 te eventi 'Learn Java...' (duhet 'Eventi eshte i plote').");
    }

    private Eventi krijoEvent(String titulli, String pershkrimi,
                             LocalDate data, LocalTime ora,
                             String vendndodhja,
                             int kufiri,
                             Perdoruesi host,
                             KategoriAftesie kategori) {

        Eventi e = new Eventi();
        e.setTitulli(titulli);
        e.setPershkrimi(pershkrimi);
        e.setData(data);
        e.setOra(ora);
        e.setVendndodhja(vendndodhja);
        e.setKufiriPjesemarresve(kufiri);
        e.setStatusi(StatusEventi.AKTIV);
        e.setHost(host);
        e.setKategoria(kategori);
        return e;
    }
}

/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.api.ekadasi;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Structural sweep of the 26 {@code IEkadasi} leaves (24 regular + Padmini/Parama for the
 * leap lunar month, Adhika Maasa).
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
class EkadasiTest {

    static abstract class EkadasiStub implements IEkadasi {
        public IEkadasi[] all() { return new IEkadasi[0]; }
        public int ordinal() { return 0; }
        public String name() { return getClass().getSimpleName(); }
    }

    static class Utpanna extends EkadasiStub implements IUtpannaEkadasi {}
    static class Moksada extends EkadasiStub implements IMoksadaEkadasi {}
    static class Saphala extends EkadasiStub implements ISaphalaEkadasi {}
    static class Putrada extends EkadasiStub implements IPutradaEkadasi {}
    static class Sattila extends EkadasiStub implements ISattilaEkadasi {}
    static class Jaya extends EkadasiStub implements IJayaEkadasi {}
    static class Vijaya extends EkadasiStub implements IVijayaEkadasi {}
    static class Amalaki extends EkadasiStub implements IAmalakiEkadasi {}
    static class Paapmochani extends EkadasiStub implements IPaapmochaniEkadasi {}
    static class Kamada extends EkadasiStub implements IKamadaEkadasi {}
    static class Varuthini extends EkadasiStub implements IVaruthiniEkadasi {}
    static class Mohini extends EkadasiStub implements IMohiniEkadasi {}
    static class Apara extends EkadasiStub implements IAparaEkadasi {}
    static class Nirjala extends EkadasiStub implements INirjalaEkadasi {}
    static class Yogini extends EkadasiStub implements IYoginiEkadasi {}
    static class Sayana extends EkadasiStub implements ISayanaEkadasi {}
    static class Kamika extends EkadasiStub implements IKamikaEkadasi {}
    static class Pavitropana extends EkadasiStub implements IPavitropanaEkadasi {}
    static class Annada extends EkadasiStub implements IAnnadaEkadasi {}
    static class Parsva extends EkadasiStub implements IParsvaEkadasi {}
    static class Indira extends EkadasiStub implements IIndiraEkadasi {}
    static class Pasankusa extends EkadasiStub implements IPasankusaEkadasi {}
    static class Rama extends EkadasiStub implements IRamaEkadasi {}
    static class Utthana extends EkadasiStub implements IUtthanaEkadasi {}
    static class Padmini extends EkadasiStub implements IPadminiEkadasi {}
    static class Parama extends EkadasiStub implements IParamaEkadasi {}

    static List<IEkadasi> allTwentySix() {
        return Arrays.asList(new Utpanna(), new Moksada(), new Saphala(), new Putrada(), new Sattila(),
                new Jaya(), new Vijaya(), new Amalaki(), new Paapmochani(), new Kamada(), new Varuthini(),
                new Mohini(), new Apara(), new Nirjala(), new Yogini(), new Sayana(), new Kamika(),
                new Pavitropana(), new Annada(), new Parsva(), new Indira(), new Pasankusa(), new Rama(),
                new Utthana(), new Padmini(), new Parama());
    }

    @Test
    void twentySixLeaves_haveUniqueFidsOneThroughTwentySix() {
        TreeSet<Integer> fids = new TreeSet<>();
        for (IEkadasi e : allTwentySix()) fids.add(e.fid());
        assertEquals(26, fids.size());
        assertEquals(1, fids.first().intValue());
        assertEquals(26, fids.last().intValue());
    }

    @Test
    void twentySixLeaves_codeIsEKPrefixPlusFid() {
        for (IEkadasi e : allTwentySix()) assertEquals("EK" + e.fid(), e.code());
    }

    @Test
    void twentySixLeaves_lengthIsTwentyFour() {
        for (IEkadasi e : allTwentySix()) assertEquals(24., e.length(), 1e-9);
    }

    @Test
    void padminiAndParama_areTheAdhikaMaasaExtraPair() {
        // the two extra ekadasis specific to the leap lunar month, appended after the 24
        // regular ones
        assertEquals(25, new Padmini().fid());
        assertEquals(26, new Parama().fid());
    }
}

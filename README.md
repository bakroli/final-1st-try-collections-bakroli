# Spam szűrő
## Bevezetés
Spam szűrőt kell írnod a `SpamFilter` osztályban, az `applyRules` metódusban.

## Feladatleírás

A mondat ebben a kontextusban egy szavakból álló listák listája: `List<List<String>>`.

Van két lista, ami alapján el kell dönteni, hogy mely mondatok elfogadhatóak. A `whitelist` változóban olyan szavakat kapsz, amiből legalább
egynek elő kell fordulnia a mondatban ahhoz, hogy a mondat érvényes legyen.
A `blacklist` változóban olyan szavakat kapsz, ami közül ha előfordul csupán egy is, a mondat érvénytelen.

Ezt a két változót az osztályod konstruktora kapja meg paraméterként.
Ha csupán egyikük is `null`, dobj `IllegalArgumentException`-t.


Az `applyRules` metódus paramétere egy mondatokból álló lista. Az `applyRules` kiszűrí a fent leírt szabályoknak nem megfelelő mondatokat,
és visszatér az engedélyezett mondatok listával. A mondatok sorrendjén nem változtat.

Használj megfelelő adattípusokat, hogy a kódod gyorsan lefusson!
Gondold végig, milyen hatással van a program teljesítményére,
ha pl.: a `blacklist`-ben ugyanazok a szavak sokszor ismétlődnek?

A munka során a kísérletezéshez a `SpamFilter` osztályban létrehozhatsz `main` metódust,
illetve készíthetsz másik osztályt `main` metódussal.

A teszteket nem szabad módosítani!

Példa:

```java
import hu.nive.ujratervezes.spamfiltering.SpamFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Main {
    public static void main(String[] args) {
        List<String> whiteList = new ArrayList<>(Arrays.asList("kutya", "cica", "aranyos"));
        List<String> blackList = new ArrayList<>(Arrays.asList("csunya", "budos", "buta"));

        SpamFilter spamFilter = new SpamFilter(whiteList, blackList);
        List<List<String>> sentences = Arrays.asList(
                Arrays.asList("a", "cica", "kedves"),
                Arrays.asList("a", "nyul", "furge"),
                Arrays.asList("a", "kutya", "budos"),
                Arrays.asList("a", "kutya", "huseges")
        );

        List<List<String>> result = spamFilter.applyRules(sentences);
        System.out.println(result);
    }
}
```

Kimenet: ``[[a, cica, kedves], [a, kutya, huseges]]``
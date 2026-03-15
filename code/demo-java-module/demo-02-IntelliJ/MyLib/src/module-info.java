module demo.lib {
    // requires java.sql;
    requires transitive java.sql;

    exports org.example.lib;

    // opens org.example.lib;
}
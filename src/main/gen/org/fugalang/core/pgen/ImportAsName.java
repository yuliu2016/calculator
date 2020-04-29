package org.fugalang.core.pgen;

// import_as_name: 'NAME' ['as' 'NAME']
public class ImportAsName {
    public final Object name;
    public final ImportAsName2Group importAsName2Group;

    public ImportAsName(
            Object name,
            ImportAsName2Group importAsName2Group
    ) {
        this.name = name;
        this.importAsName2Group = importAsName2Group;
    }
}

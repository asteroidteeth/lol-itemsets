using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using System.Web.Script.Serialization;

namespace lol_itemsets_lib
{
    using System;
    using System.Resources;
    using System.IO;

    public class ItemSet
    {
        private static ItemSet instance;
        private List<Item> items;

        private ItemSet()
        {
            JavaScriptSerializer ser = new JavaScriptSerializer();
            String json = File.ReadAllText(@"Data/items.json");
            items = ser.Deserialize<List<Item>>(json);
        }

        public static ItemSet Instance
        {
            get
            {
                if (instance == null)
                {
                    instance = new ItemSet();
                }
                return instance;
            }
        }
    }
}

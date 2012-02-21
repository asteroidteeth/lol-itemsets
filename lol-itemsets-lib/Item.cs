using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace lol_itemsets_lib
{
    public class Item
    {
        String Name { get; set; }
        int ItemCode { get; set; }
        int Cost { get; set; }
        List<Item> Recipe { get; set; }
        String Description { get; set; }
    }
}

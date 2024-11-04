package confectionery;

public class ConfectioneryController {

    private final ConfectioneryService confectioneryService ;

    public ConfectioneryController(ConfectioneryService confectioneryService) {
        this.confectioneryService = confectioneryService;
    }

    public void viewMenu() {
        StringBuilder output = new StringBuilder("Menu:\n");
        confectioneryService.getMenu().forEach(product -> output.append(product.toString()).append("\n"));
        System.out.println(output);
    }


   }

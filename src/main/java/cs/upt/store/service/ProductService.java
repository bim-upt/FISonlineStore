package cs.upt.store.service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NameNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import cs.upt.store.DTO.HashedUserDTO;
import cs.upt.store.DTO.ProductDTO;
import cs.upt.store.exceptions.UserIsNotASellerException;
import cs.upt.store.model.Product;
import cs.upt.store.repository.ProductRepository;



@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;

    public Product insertProduct(Product product) throws NoSuchAlgorithmException, NameNotFoundException, UserIsNotASellerException, DataIntegrityViolationException{
        try{
            HashedUserDTO seller = userService.getUser(product.getSeller());
            if(seller.getType() == 0){
                throw new UserIsNotASellerException("Not a seller");
            }
            List<Product> sellerProducts = productRepository.findBySeller(product.getSeller());
            for (int i = 0; i < sellerProducts.size(); i++) {
                //System.out.println(sellerProducts.get(i).getName());
                if(sellerProducts.get(i).getCode().equals(product.getCode())){
                    throw new DataIntegrityViolationException("Seller already has a product with this code");
                }
            }
            return productRepository.insert(product);
        }catch(NameNotFoundException e){
            throw e;
        }catch(Exception e){
            throw e;
        }
    }

    public Product modifyProduct(Product product) throws NotFoundException{
        List<Product> sellerProducts = productRepository.findBySeller(product.getSeller());
        Product found = null;
        for (int i = 0; i < sellerProducts.size(); i++) {
            if(sellerProducts.get(i).getCode().equals(product.getCode())){
                found = sellerProducts.get(i);
                break;
            }
        }
        if(found == null){
            throw new NotFoundException();
        }
        found.setImgs(product.getImgs());
        found.setName(product.getName());
        found.setPrice(product.getPrice());
        return productRepository.save(found);
    }

    public Product deleteProduct(String code, String seller) throws NotFoundException{
        Product found = null;
        List<Product> sellerProducts = productRepository.findBySeller(seller);
        for (int i = 0; i < sellerProducts.size(); i++) {
            if(sellerProducts.get(i).getCode().equals(code)){
                found = sellerProducts.get(i);
                break;
            }
        }
        if(found == null){
            throw new NotFoundException();
        }
        productRepository.deleteById(found.getPid());
        return found;
    }

    public List<ProductDTO> getProducts(){
        List<Product> all = productRepository.findAll();
        List<ProductDTO> result = new ArrayList<ProductDTO>();
        for (int i = 0; i < all.size(); i++) {
            result.add(new ProductDTO(all.get(i), true, null));
        }
        return result;
    }
}

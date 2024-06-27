package gift;

import org.springframework.dao.DuplicateKeyException;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class ProductDAO {
    private Map<Long, ProductRecord> records = new HashMap<Long, ProductRecord>();
    private long idTraker = 1;

    private boolean isRecordExist(long id) {
        if (records.containsKey(id)) {
            return true;
        }

        return false;
    }

    private long getNewId() {
        if (isRecordExist(idTraker)) {
            idTraker++;
        }

        return idTraker;
    }

    public ProductRecord addNewRecord(ProductRecord product, long id) {
        if (isRecordExist(id)) {
            //이미 해당 id가 존재하면 null반환. @TODO 예외를 던지도록 리팩토링
            return null;
        }

        ProductRecord record = new ProductRecord(id, product.name(), product.price(), product.imageUrl());
        records.put(id, record);

        return record;
    }

    public ProductRecord addNewRecord(ProductRecord product) {
        return addNewRecord(product, getNewId());
    }

    public ProductRecord[] getAllRecords() {
        return records.values().toArray(new ProductRecord[records.size()]);
    }

    public void deleteRecord(long id) throws NoSuchElementException {
        if (!isRecordExist(id)) {
            throw new NoSuchElementException("Record not found");
        }
        records.remove(id);
    }
}

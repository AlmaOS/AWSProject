package com.project.RestAppAWS.repositories;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.project.RestAppAWS.schema.InicioSesion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RepositorySesiones {
    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public InicioSesion save(InicioSesion sesion){
        dynamoDBMapper.save(sesion);
        return sesion;
    }

    public InicioSesion getSessionBySessionString(String sessionString){
        InicioSesion sesion = null;
        Map<String, AttributeValue> eav= new HashMap<String ,AttributeValue>();
        eav.put(":sessionString", new AttributeValue().withS(sessionString));

        DynamoDBScanExpression scanExpression=new DynamoDBScanExpression()
                .withFilterExpression("sessionString = :sessionString")
                .withExpressionAttributeValues(eav);
        List<InicioSesion> useResult = dynamoDBMapper.scan(InicioSesion.class, scanExpression);
        if(!useResult.isEmpty() && useResult.size()>0) {
            sesion=useResult.get(0);
        }
        return sesion;
    }
}

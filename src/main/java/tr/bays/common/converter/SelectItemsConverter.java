package tr.bays.common.converter;

import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.stereotype.Component;

import tr.bays.common.base.BaseDto;


@Component("selectItemsConverter")
public class SelectItemsConverter implements Converter {

    private static Map<Object, String> entities = new WeakHashMap<Object, String>();
    private static Map<String, Object> entitiesViceVersa = new WeakHashMap<String, Object>();

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object entity) {
        synchronized (entities) {
        	String str = entity.toString();
        	if (entity instanceof BaseDto) {
				BaseDto baseDto = (BaseDto) entity;
				str += baseDto.getId();
			}
            if (!entities.containsKey(str)) {
                String uuid = UUID.randomUUID().toString();
                entities.put(str, uuid);    
                entitiesViceVersa.put(uuid, entity);
                return uuid;
            } else {
                return entities.get(str);
            }
            
        }
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String uuid) {
        if(entitiesViceVersa.containsKey(uuid))
        	return entitiesViceVersa.get(uuid);
        return null;
    }
}

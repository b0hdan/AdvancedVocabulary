package com.dubyniak.bohdan.resources;

import com.dubyniak.bohdan.api.WordCard;
import com.dubyniak.bohdan.core.WordCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;

@Controller
@Path("/wordCards")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class WordCardResource {
    private final WordCardService wordCardService;

    @Autowired
    public WordCardResource(WordCardService wordCardService) {
        this.wordCardService = wordCardService;
    }

    @GET
    public Response getWordCards() {
        List<WordCard> wordCards = wordCardService.getWordCards();
        return wordCards.size() > 0 ? Response.ok(wordCards).build() : Response.noContent().build();
    }

    @GET
    @Path("/{id}")
    public Response getWordCard(@PathParam("id") Long id) {
        return Response.ok(wordCardService.getWordCard(id)).build();
    }

    @POST
    public Response createWordCard(WordCard wordCard) {
        WordCard card = wordCardService.createWordCard(wordCard);
        URI uri = UriBuilder.fromResource(WordCardResource.class).path(card.getId().toString()).build();
        return Response.created(uri).entity(card).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteWordCard(@PathParam("id") Long id) {
        wordCardService.deleteWordCard(id);
        return Response.noContent().build();
    }

    @PUT
    @Path("/{id}")
    public Response updateWordCard(@PathParam("id") Long id, WordCard wordCard) {
        WordCard card = wordCardService.updateWordCard(id, wordCard);
        return Response.ok(card).build();
    }
}
